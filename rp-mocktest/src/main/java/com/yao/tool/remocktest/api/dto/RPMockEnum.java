package com.yao.tool.remocktest.api.dto;

public enum RPMockEnum {
    /**
     * REPLAY: 回放
     *
     */
    REPLAY(0, "REPLAY"),

    /**
     * RECORD:记录文件，即mock模式
     *
     */
    RECORD(1, "RECORD"),

    ;

    /**
     * Field _memberTable.
     */
    private static java.util.Hashtable _memberTable = init();

    /**
     * Method init.
     *
     * @return the initialized Hashtable for the member table
     */
    private static java.util.Hashtable init() {
        java.util.Hashtable members = new java.util.Hashtable();
        members.put(REPLAY.type, REPLAY);
        members.put(RECORD.type, RECORD);
        return members;

    }

    /**
     * isValidType: 判断是否是有效的枚举类型
     *
     * @param type
     * @return boolean
     * @author：micat707
     */
    public static boolean isValidType(String type) {
        boolean result = false;
        if (type != null) {
            Object obj = _memberTable.get(type);
            if (obj != null) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 枚举序号
     */
    private int index;
    /**
     * mock 类型
     */
    private String type;

    // 构造方法
    private RPMockEnum(int index, String type) {
        this.index = index;
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return this.name();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
