package models;

import utils.Utilities;

public class EducationApp extends App {
    /**
     * 教育级别： 取值范围[1,10]
     */
    private int level = 0;

    /**
     * 构造函数，要加上 level
     * @param developer
     * @param appName
     * @param appSize
     * @param appVersion
     * @param appCost
     * @param level
     */
    public EducationApp(Developer developer, String appName, double appSize, double appVersion, double appCost, int level) {
        super(developer, appName, appSize, appVersion, appCost);
        // 要对level进行数据验证处理
        if (Utilities.validRange(level,1,10)){
            this.level = level;
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (Utilities.validRange(level,1,10)){
            this.level = level;
        }
    }

    /**
     * 是推荐的应用程序
     * @return
     */
    @Override
    public boolean isRecommendedApp() {
        if (super.getAppCost() > 0.99 && super.calculateRating() >=3.5
        && level >= 3){
            return true;
        }
        return false;
    }

    @Override
    public String appSummary() {
        return "level "+level+ super.getAppName() + "(V"+super.getAppVersion() + ", "+super.getDeveloper()
                +",€"+super.getAppCost() + ",Rating: "+super.calculateRating();
    }

    @Override
    public String toString() {
        return super.getAppName() + "(Version " + super.getAppVersion() +
                super.getDeveloper().toString() +  super.getAppSize() + "MB"+
                "Cost: " + super.getAppCost()   + "Level: " + level +
                "Ratings (" + super.calculateRating() + super.ratings.toString();
    }
}
