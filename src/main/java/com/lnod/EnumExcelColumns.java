package com.lnod;

public enum EnumExcelColumns {
	EMAIL_ID  (1),
    CLASSROOM_SESSIONS_ATTENDED(2),
    ONLINE_COURSES_ENROLLED(3);

    private final int columnIndex;

    private EnumExcelColumns(int columnIndex) {
        this.columnIndex = columnIndex;
    }
	
    public int getColumnIndex() {
        return this.columnIndex;
    }
    
}