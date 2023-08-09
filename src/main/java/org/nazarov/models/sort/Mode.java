package org.nazarov.models.sort;

import java.util.HashMap;
import java.util.Map;

public class Mode {
    private Map<String, DataTypeSort> dataTypeSort = new HashMap<>() {{
        put("-i", DataTypeSort.INTEGER);
        put("-s", DataTypeSort.STRING);
    }};

    private Map<String, ModeSort> modeSort = new HashMap<>() {{
        put("-a", ModeSort.ASC);
        put("-d", ModeSort.DESC);
    }};


    public DataTypeSort getDataTypeSort(String key) {
        return dataTypeSort.get(key);
    }

    public ModeSort getModeSort(String key) {
        return modeSort.get(key);
    }
}
