package day4.aop.enums;

/**
 * 通知的等级  前置>后置
 */
public enum  LevelEnums {
    BEFORE(1,"前置通知等级"),
    AFTER(2,"后置通知等级");

    private Integer level;

   private String desc;

    LevelEnums(Integer level, String desc) {
        this.level = level;
        this.desc = desc;
    }

    public Integer getLevel() {
        return level;
    }

    public String getDesc() {
        return desc;
    }
}
