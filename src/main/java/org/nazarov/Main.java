package org.nazarov;

import org.nazarov.constants.Constants;
import org.nazarov.models.sort.Mode;
import org.nazarov.models.sort.DataTypeSort;
import org.nazarov.models.sort.ModeSort;
import org.nazarov.sort.MergeSort;
import org.nazarov.utils.Pair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.nazarov.constants.Constants.*;

public class Main {

    public static void addValue(List<Pair<?, Integer>> array, String value, Integer numberArray, DataTypeSort dataTypeSort) {
        if (dataTypeSort == DataTypeSort.INTEGER) {
            array.add(new Pair<>(Integer.valueOf(value), numberArray));
        } else {
            array.add(new Pair<>(value, numberArray));
        }
    }

    public static boolean comparePrevCurrentValue(String current, String prev, ModeSort modeSort, DataTypeSort dataTypeSort) {
        if (dataTypeSort == DataTypeSort.INTEGER) {
            int currentInt = Integer.parseInt(current);
            int prevInt = Integer.parseInt(prev);
            if (modeSort == ModeSort.ASC) {
                return currentInt >= prevInt;
            }
            return currentInt <= prevInt;
        }

        if (modeSort == ModeSort.ASC) {
            return current.compareTo(prev) >= 0;
        }
        return current.compareTo(prev) <= 0;
    }

    public static void main(String[] args) {


        ModeSort modeSort;
        DataTypeSort dataTypeSort;
        Mode mode = new Mode();
        int k = 0;
        modeSort = mode.getModeSort(args[k]);
        if (modeSort == null) {
            modeSort = ModeSort.ASC;
        } else {
            k++;
        }

        dataTypeSort = mode.getDataTypeSort(args[k]);
        k++;

        String nameOutputFile = args[k];
        k++;

        List<String> namesInputFiles = new ArrayList<>();

        for (int i = k; i < args.length; i++) {
            namesInputFiles.add(args[i]);
        }

        List<Pair<?, Integer>> array = new ArrayList<>();
        List<Pair<?, Integer>> outliers = new ArrayList<>();

        boolean conditionSortOrder = true;
        for (int i = 0; i < namesInputFiles.size(); i++) {
            String prevLine = null;
            String nameFile = namesInputFiles.get(i);
            Path path = Paths.get(INPUTS_FOLDER + nameFile);

            try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                String currentLine = null;
                while ((currentLine = reader.readLine()) != null) {
                    if (prevLine != null) {
                        conditionSortOrder = comparePrevCurrentValue(currentLine, prevLine, modeSort, dataTypeSort);
                    } else {
                        conditionSortOrder = true;
                    }

                    if (currentLine.contains(" ")) {
                        System.out.println("Warning: line has space. " + currentLine);
                        continue;
                    }

                    if (!conditionSortOrder) {
                        System.out.println("Warning: sort of order was violated in file " + nameFile + ". With value " + currentLine);
                        addValue(outliers, currentLine, null, dataTypeSort);
                        continue;
                    }

                    addValue(array, currentLine, i, dataTypeSort);
                    prevLine = currentLine;
                }
            } catch (IOException ex) {
                System.out.println("Error: Can't read file " + nameFile);
            }
        }

        if (array.size() == 0) {
            System.out.println("Error: Can't read all files");
            System.exit(0);
        }

        for (int i = 0; i < outliers.size(); i++) {
            addValue(array, String.valueOf(outliers.get(i).getFirst()), null, dataTypeSort);
        }

        MergeSort mergeSort = new MergeSort();
        mergeSort.merge(0, array.size() - 1, array, modeSort);

        Path outputPath = Paths.get(nameOutputFile);
        int retry = 0;
        while (retry < MAX_RETRY_WRITE_FILE) {
            try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
                for (int i = 0; i < array.size(); i++) {
                    writer.write(array.get(i).getFirst().toString());
                    writer.write("\n");
                }
                break;
            } catch (IOException ex) {
                System.out.println("Error: Can't write in output file. Attempt " + retry + "\\" + MAX_RETRY_WRITE_FILE);
                retry++;
            }
        }
    }
}
