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

        for (int i = 0; i < namesInputFiles.size(); i++) {
            String nameFile = namesInputFiles.get(i);
            Path path = Paths.get("examples/" + nameFile);
            try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                String currentLine = null;
                while ((currentLine = reader.readLine()) != null) {
                    if (currentLine.contains(" ")) {
                        System.out.println("Warning: line has space. " + currentLine);
                        continue;
                    }
                    if (dataTypeSort == DataTypeSort.INTEGER) {
                        array.add(new Pair<>(Integer.valueOf(currentLine), i));
                    } else {
                        array.add(new Pair<>(currentLine, i));
                    }
                }
            } catch (IOException ex) {
                System.out.println("Error: Can't read file " + nameFile);
            }
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