package models;


import utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public abstract class App {
    // 开发者
    private Developer developer;
    // 在系统中是唯一的，用工具类进行判断
    private String appName = "No app name";
    // 取值范围是 [1.1000]
    private double appSize  = 0;
    // 版本号 >= 1.0
    private double appVersion = 1.0;
    // 非负数
    private double appCost = 0;
    // 包含对一个应用程序所有的评分
    List<Rating> ratings = new ArrayList<>();

    public App(Developer developer,String appName,double appSize,double appVersion,double appCost){
        this.developer = developer;
        this.appName = appName;
        if (Utilities.validRange(appSize,1,1000)){
            this.appSize = appSize;
        }
        if (Utilities.greaterThanOrEqualTo(appVersion,1.0)){
            this.appVersion = appVersion;
        }
        if (Utilities.greaterThanOrEqualTo(appCost,0)){
            this.appCost = appCost;
        }
    }

    // 有3个抽象函数
    public abstract boolean isRecommendedApp();

    // 包含：程序名，程序版本，开发者，程序价格，总评分 使用calculateRating函数
    public abstract String appSummary();

    // 添加的处理
    public boolean addRating(Rating rating){
        boolean add = ratings.add(rating);
        return add;
    }

    public String listRatings(){
        String s = ratings.toString();
        return s;
    }

    /**
     * 没有添加评分则返回0，反之计算平均值
     * @return
     */
    public double calculateRating(){
        double sum = 0.0;
        int count = 0;
        for (Rating rating:ratings) {
            if (rating.getNumberOfStars()!= 0){
                sum = sum + rating.getNumberOfStars();
                count++;
            }
        }
        return sum/count*1.0;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public double getAppSize() {
        return appSize;
    }

    public void setAppSize(double appSize) {
        if (Utilities.validRange(appSize,1,1000)){
            this.appSize = appSize;
        }
    }

    public double getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(double appVersion) {
        if (Utilities.greaterThanOrEqualTo(appVersion,1.0)){
            this.appVersion = appVersion;
        }
    }

    public double getAppCost() {
        return appCost;
    }

    public void setAppCost(double appCost) {
        if (Utilities.greaterThanOrEqualTo(appCost,0)){
            this.appCost = appCost;
        }
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    @Override
    public abstract String toString() ;
}
