package org.nazarov;

import org.nazarov.models.sort.Mode;
import org.nazarov.models.sort.DataTypeSort;
import org.nazarov.models.sort.ModeSort;

import java.util.ArrayList;
import java.util.List;

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

        List<String> namesInputFiles = new ArrayList<>();

        for (int i = k; i < args.length; i++) {
            namesInputFiles.add(args[i]);
        }



    }
}