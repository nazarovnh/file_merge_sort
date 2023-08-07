package org.nazarov.models.sort;

import java.util.Map;

public class Mode {
    private Map<String, DataTypeSort> dataTypeSort = Map.of(
            "i", DataTypeSort.INTEGER,
            "s", DataTypeSort.STRING
    );

    private Map<String, ModeSort> modeSort = Map.of(
            "a", ModeSort.ASC,
            "d", ModeSort.DESC
    );

    public DataTypeSort getDataTypeSort(String key) {
        return dataTypeSort.get(dataTypeSort.get(key));
    }

    public ModeSort getModeSort(String key) {
        return modeSort.get(modeSort.get(key));
    }
}
