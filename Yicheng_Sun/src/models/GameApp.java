package models;

public class GameApp extends App{
    private boolean isMultiplayer = false;

    public GameApp(Developer developer, String appName, double appSize, double appVersion, double appCost, boolean isMultiplayer) {
        super(developer, appName, appSize, appVersion, appCost);
        this.isMultiplayer = isMultiplayer;
    }

    public boolean isMultiplayer() {
        return isMultiplayer;
    }

    public void setMultiplayer(boolean multiplayer) {
        isMultiplayer = multiplayer;
    }

    /**
     * 是推荐的应用程序
     * @return
     */
    @Override
    public boolean isRecommendedApp() {
        if ( super.calculateRating() >= 4.0 && isMultiplayer){
            return true;
        }
        return false;
    }

    @Override
    public String appSummary() {
        return "Multiplayer "+isMultiplayer+ super.getAppName() + "(V"+super.getAppVersion() + ", "+super.getDeveloper()
                +",€"+super.getAppCost() + ",Rating: "+super.calculateRating();
    }

    @Override
    public String toString() {
        return super.getAppName() + "(Version " + super.getAppVersion() +
                super.getDeveloper().toString() +  super.getAppSize() + "MB"+
                "Cost: " + super.getAppCost()   + "Multiplayer: " + isMultiplayer +
                "Ratings (" + super.calculateRating() + super.ratings.toString();
    }
}
