package models;

// 生产力的App
public class ProductivityApp extends App{

    public ProductivityApp(Developer developer, String appName, double appSize, double appVersion, double appCost) {
        super(developer, appName, appSize, appVersion, appCost);
    }

    @Override
    public boolean isRecommendedApp() {
        if (super.getAppCost() >= 1.99 && super.calculateRating() >=3.0){
            return true;
        }
        return false;
    }

    @Override
    public String appSummary() {
        return  super.getAppName() + "(V"+super.getAppVersion() + ", "+super.getDeveloper()
                +",€"+super.getAppCost() + ",Rating: "+super.calculateRating();
    }


    @Override
    public String toString() {
        return super.getAppName() + "(Version " + super.getAppVersion() +
                super.getDeveloper().toString() +  super.getAppSize() + "MB"+
                "Cost: " + super.getAppCost()   +
                "Ratings (" + super.calculateRating() + super.ratings.toString();
    }
}
