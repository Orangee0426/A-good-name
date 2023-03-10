package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.*;
import utils.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.random;
import static utils.RatingUtility.generateRandomRating;

public class AppStoreAPI {

    List<App> apps = new ArrayList<>();

    public boolean addApp(App app){
        return apps.add(app);
    }

    public String listAllApps(){
        if (numberOfApps() == 0){
            return "No apps";
        }
        String str = "";
        for (App app : apps ) {
            str += apps.indexOf(app) +": "+app.toString() + "\n";
        }
        return str;
    }

    public String listSummaryOfAllApps(){
        if (numberOfApps() == 0){
            return "No apps";
        }
        String str = "";
        for (App app : apps ) {
            str += apps.indexOf(app) +": "+app.appSummary() + "\n";
        }
        return str;
    }

    public String listAllGameApps(){
        String str = "";
        for (App app : apps ) {
            if (app instanceof GameApp) {
                str += apps.indexOf(app) + ": " + app.toString() + "\n";
            }
        }
        if (str.isEmpty()) {
            return "No Game apps";
        } else {
            return str;
        }
    }

    public String listAllEducationApps(){
        String str = "";
        for (App app : apps ) {
            if (app instanceof EducationApp) {
                str += apps.indexOf(app) + ": " + app.toString() + "\n";
            }
        }
        if (str.isEmpty()) {
            return "No Education apps";
        } else {
            return str;
        }
    }

    public String listAllProductivityApps(){
        String str = "";
        for (App app : apps ) {
            if (app instanceof ProductivityApp) {
                str += apps.indexOf(app) + ": " + app.toString() + "\n";
            }
        }
        if (str.isEmpty()) {
            return "No Productivity apps";
        } else {
            return str;
        }
    }

    /**
     * ????????????????????????????????????????????????
     * @param name
     * @return
     */
    public String listAllAppsByName(String name){
        String str = "";
        for (App app : apps ) {
            if (app.getAppName().contentEquals(name)){
                str += apps.indexOf(app) + ": " + app.toString() + "\n";
            }
        }
        if (str.isEmpty()) {
            return "No apps for name "+name+" exists";
        } else {
            return str;
        }
    }

    /**
     * ???????????????????????????????????????????????????
     * ???????????????????????????????????????
     * @param rating
     * @return
     */
    public String listAllAppsAboveOrEqualAGivenStarRating(int rating){
        String str = "";
        for (App app : apps ) {
            List<Rating> ratings = app.getRatings();
            int sum  = 0;
            for (Rating r : ratings) {
                if (r.getNumberOfStars() >= rating ){
                    sum++;
                }
            }
            if (sum == ratings.size() && ratings.size() >0){
                str += apps.indexOf(app) + ": " + app.toString() + "\n";
            }
        }
        if (str.isEmpty()) {
            return  "No apps have a rating of " + rating + " or above";
        } else {
            return str;
        }
    }


    /**
     * ?????????????????????
     */
    public String listAllRecommendedApps(){
        String str = "";
        for (App app : apps ) {
            // ?????????
            if (app.isRecommendedApp()){
                str += apps.indexOf(app) + ": " + app.toString() + "\n";
            }
        }
        if (str.isEmpty()) {
            return "No recommended apps";
        } else {
            return str;
        }
    }


    public String listAllAppsByChosenDeveloper(Developer developer){
        String str = "";
        String developerName = developer.getDeveloperName();
        for (App app : apps ) {
            if (app.getDeveloper().getDeveloperName().equals(developerName)){
                str += apps.indexOf(app) + ": " + app.toString() + "\n";
            }
        }
        if (str.isEmpty()) {
            return "No apps for developer: " + developer;
        } else {
            return str;
        }

    }

    public int numberOfAppsByChosenDeveloper(Developer developer){
        int sum = 0;
        String developerName = developer.getDeveloperName();
        for (App app: apps){
            if (app.getDeveloper().getDeveloperName().equals(developerName)) {
                sum++;
            }
        }
        return sum;
    }

    public App deleteAppByIndex(int index){
        if (index < numberOfApps()){
            App remove = apps.remove(index);
            return remove;
        }else {
            return null;
        }
    }

    /**
     * ??????????????????
     * @return
     */
    public App randomApp(){
        if (numberOfApps() == 0){
            return null;
        }
        Random rand = new Random();
        int temp = rand.nextInt(numberOfApps());
        App app = apps.get(temp);
        if (app!=null){
            return app;
        }else {
            return null;
        }
    }

    // ??????????????????
    public void simulateRatings(){
        Random rand = new Random();
        double temp = rand.nextDouble();
        for (App app :apps) {
            app.setAppCost(temp);
        }
    }

    public boolean isValidAppName(String name){
        App appByName = getAppByName(name);
        if (appByName!= null){
            return true;
        }else {
            return false;
        }
    }

    /**
     * ???????????????
     * @param name
     * @return
     */
    public App getAppByName(String name){
        for (App app:apps) {
            boolean b = app.getAppName().equalsIgnoreCase(name);
            if (b){
                return app;
            }
        }
        return null;
    }


    public App getAppByIndex(int index){
        App app = apps.get(index);
        if (app != null){
            return app;
        }
        return null;
    }

    public boolean isValidIndex(int index){
        if (index> numberOfApps()){
            return false;
        }
        App appByIndex = getAppByIndex(index);
        if (appByIndex != null){
            return true;
        }else {
            return false;
        }
    }

    public int numberOfApps(){
        return apps.size();
    }

    /**
     * ?????????????????? apps ???????????????????????????????????????
     */
    public void sortAppsByNameAscending(){
        // ????????????
        for(int i=0;i<numberOfApps()-1;i++) {
            for(int j=0;j<numberOfApps()-i-1;j++) {
                if(apps.get(j).getAppName().length()>apps.get(j+1).getAppName().length()) {
                    swapApps(apps,j,j+1);
                }
            }
        }

    }

    /**
     *     ???????????????????????????????????????????????????????????????
     *     ??? i ??? j ?????????????????????????????????????????????????????????
     * @param apps
     * @param i
     * @param j
     */
    public void swapApps(List apps, int i, int j){
        // ??????
        App appByIndex = getAppByIndex(i);
        App appByIndex1 = getAppByIndex(j);
        // ?????????
        apps.set(i,"");
        apps.set(j,"");
        // ??????
        apps.set(i,appByIndex1);
        apps.set(j,appByIndex);
    }



    //---------------------
    // Method to simulate ratings (using the RatingUtility).
    // This will be called from the Driver (see skeleton code)
    //---------------------
    // TODO UNCOMMENT THIS COMPLETED method as you start working through this class
    //---------------------
    /*
    public void simulateRatings(){
        for (App app :apps) {
            app.addRating(generateRandomRating());
        }
    }*/

    //---------------------
    // Validation methods
    //---------------------
    // ????????????????????????????????????????????????COMPLETED??????
    // TODO UNCOMMENT THIS COMPlETED method as you start working through this class
    //---------------------
    /*
    public boolean isValidIndex(int index) {
        return (index >= 0) && (index < apps.size());
    }*/

    //---------------------
    // Persistence methods
    //---------------------
    // TODO ?????????????????????????????????????????????????????????????????????
    //---------------------

    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{App.class, EducationApp.class, GameApp.class, ProductivityApp.class, Rating.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(fileName()));
        apps = (List<App>) in.readObject();
        in.close();
    }

    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(fileName()));
        out.writeObject(apps);
        out.close();
    }

    public String fileName(){
        return "apps.xml";
    }
}
