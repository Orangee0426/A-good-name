package main;

import controllers.AppStoreAPI;
import controllers.DeveloperAPI;
import models.*;
import utils.ScannerInput;

public class Driver {

    //TODO Some skeleton code has been given to you.
    //     Familiarise yourself with the skeleton code...run the menu and then review the skeleton code.
    //     Then start working through the spec.

    private DeveloperAPI developerAPI = new DeveloperAPI();
    private AppStoreAPI appStoreAPI = new AppStoreAPI();

    public static void main(String[] args) {
        new Driver().start();
    }

    public void start() {
        loadAllData();
        runMainMenu();
    }

    private int mainMenu() {
        System.out.println("""
                 -------------App Store------------
                |  1) Developer - Management MENU  |
                |  2) App - Management MENU        |
                |  3) Reports MENU                 |
                |----------------------------------|
                |  4) Search                       |
                |  5) Sort                         |
                |----------------------------------|
                |  6) Recommended Apps             |
                |  7) Random App of the Day        |
                |  8) Simulate ratings             |
                |----------------------------------|
                |  20) Save all                    |
                |  21) Load all                    |
                |----------------------------------|
                |  0) Exit                         |
                 ----------------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private int ManagementMenu(){
        System.out.println("""
                 -------------App Store------------
                |  1) Add an app                  |
                |  2) Update app                  |
                |  3) Delete app                  |
                |  0) return to main menu         |
                 ----------------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private int ResportsMenu(){
        System.out.println("""
                 -------------Reports Menu------------
                |  1) Apps Overview                  |
                |  2) Developers Overview            |
                |  0) RETURN to main menu             |
                 ----------------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }



    private void runMainMenu() {
        int option = mainMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runDeveloperMenu();
                case 2 -> runManagementMenu();
                case 3 -> runResportsMenu();
                case 4 -> searchAppsBySpecificCriteria();
                case 5 -> seleceByNameSort();
                case 6 -> selectAllRecommendedApps();
                case 7 -> selectRandomApp();
                case 8 -> simulateRatings();
                case 20 -> saveAllData();
                case 21 -> loadAllData();
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = mainMenu();
        }
        exitApp();
    }

    //  ?????????????????????,??????????????????????????????
    private void seleceByNameSort(){
        appStoreAPI.sortAppsByNameAscending();
        String s = appStoreAPI.listAllApps();
        System.out.println("???????????????????????????"+s);
    }

    //  ?????????????????????
    private void selectAllRecommendedApps(){
        String s = appStoreAPI.listAllRecommendedApps();
        System.out.println("?????????????????????"+s);
    }

    //  ?????????????????????????????????  randomApp
    private void selectRandomApp(){
        App app = appStoreAPI.randomApp();
        System.out.println(app.toString());
    }

    private void exitApp() {
        saveAllData();
        System.out.println("Exiting....");
        System.exit(0);
    }

    //--------------------------------------------------
    //  Developer Management - Menu Items
    //--------------------------------------------------
    private int developerMenu() {
        System.out.println("""
                 -------Developer Menu-------
                |   1) Add a developer       |
                |   2) List developer        |
                |   3) Update developer      |
                |   4) Delete developer      |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    /**
     *                 |  1) Apps Overview                  |
     *                 |  2) Developers Overview            |
     *                 |  0) RETURN to main menu             |
     */
    private void runResportsMenu(){
        int option = ResportsMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> appsOverview(); // Apps Overview
                case 2 -> developersOverview();// Developers Overview
                case 0 -> mainMenu();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = developerMenu();
        }
    }

    private void appsOverview(){
        String s = appStoreAPI.listAllApps();
        System.out.println(s);
    }

    private void developersOverview(){
        String s = developerAPI.listDevelopers();
        System.out.println(s);
    }

    private void runDeveloperMenu() {
        int option = developerMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addDeveloper();
                case 2 -> System.out.println(developerAPI.listDevelopers());
                case 3 -> updateDeveloper();
                case 4 -> deleteDeveloper();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = developerMenu();
        }
    }

    /**
     * |  1) Add an app                  |
     * |  2) Update app                  |
     * |  3) Delete app                  |
     * |  0) return to main menu         |
     * ok
     */
    private void runManagementMenu(){
        int option = ManagementMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addApp();
                case 2 -> updateApp();
                case 3 -> deleteApp();
                case 0 -> mainMenu();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = developerMenu();
        }
    }

    /**
     *     // ?????????
     *     private Developer developer;
     *     // ???????????????????????????????????????????????????
     *     private String appName = "No app name";
     *     // ??????????????? [1.1000]
     *     private double appSize  = 0;
     *     // ????????? >= 1.0
     *     private double appVersion = 1.0;
     *     // ?????????
     *     private double appCost = 0;
     *     // ??????????????????????????????????????????
     *     List<Rating> ratings = new ArrayList<>();
     *
     *
     *      *developerName: ??????????????????
     *  * developerWebsite??? ??????????????????
     */
    private void addApp(){
        String s = ScannerInput.validNextLine("???????????????????????????");
        String s1 = ScannerInput.validNextLine("???????????????????????????");
        Developer developer = new Developer(s,s1);
        String s2 = ScannerInput.validNextLine("?????????appName???");
        double d1 = ScannerInput.validNextDouble("?????????appSize???");
        double d2 = ScannerInput.validNextDouble("?????????appVersion???");
        double d3 = ScannerInput.validNextDouble("?????????appCost???");
        ProductivityApp productivityApp =new ProductivityApp(developer,s2,d1,d2,d3);
        boolean b = appStoreAPI.addApp(productivityApp);
        if (b){
            System.out.println("????????????");
        }else {
            System.out.println("????????????");
        }
    }

    private void updateApp(){
        int i = ScannerInput.validNextInt("?????????????????????index???");
        App appByIndex = appStoreAPI.getAppByIndex(i);
        if (appByIndex == null){
            System.out.println("???????????????");
        }
        String s = ScannerInput.validNextLine("???????????????????????????");
        String s1 = ScannerInput.validNextLine("???????????????????????????");
        Developer developer = new Developer(s,s1);
        String s2 = ScannerInput.validNextLine("?????????appName???");
        double d1 = ScannerInput.validNextDouble("?????????appSize???");
        double d2 = ScannerInput.validNextDouble("?????????appVersion???");
        double d3 = ScannerInput.validNextDouble("?????????appCost???");
        appByIndex.setDeveloper(developer);
        appByIndex.setAppName(s2);
        appByIndex.setAppSize(d1);
        appByIndex.setAppCost(d3);
        appByIndex.setAppVersion(d2);
        System.out.println("????????????????????????");
    }

    private void deleteApp(){
        int i = ScannerInput.validNextInt("?????????????????????index???");
        App app = appStoreAPI.deleteAppByIndex(i);
        if (app == null){
            System.out.println("????????????");
        }
        System.out.println("????????????????????????");
    }

    private void addDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        String developerWebsite = ScannerInput.validNextLine("Please enter the developer website: ");

        if (developerAPI.addDeveloper(new Developer(developerName, developerWebsite))) {
            System.out.println("Add successful");
        } else {
            System.out.println("Add not successful");
        }
    }

    private void updateDeveloper() {
        System.out.println(developerAPI.listDevelopers());
        Developer developer = readValidDeveloperByName();
        if (developer != null) {
            String developerWebsite = ScannerInput.validNextLine("Please enter new website: ");
            if (developerAPI.updateDeveloperWebsite(developer.getDeveloperName(), developerWebsite)) {
                System.out.println("Developer Website Updated");
            } else {
                System.out.println("Developer Website NOT Updated");
            }
        } else {
            System.out.println("Developer name is NOT valid");
        }
    }

    private void deleteDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        if (developerAPI.removeDeveloper(developerName) != null) {
            System.out.println("Delete successful");
        } else {
            System.out.println("Delete not successful");
        }
    }

    private Developer readValidDeveloperByName() {
        String developerName = ScannerInput.validNextLine("Please enter the developer's name: ");
        if (developerAPI.isValidDeveloper(developerName)) {
            return developerAPI.getDeveloperByName(developerName);
        } else {
            return null;
        }
    }



    //--------------------------------------------------
    // TODO UNCOMMENT THIS CODE as you start working through this class
    //--------------------------------------------------
    private void searchAppsBySpecificCriteria() {
        System.out.println("""
                What criteria would you like to search apps by:
                  1) App Name
                  2) Developer Name
                  3) Rating (all apps of that rating or above)""");
        int option = ScannerInput.validNextInt("==>> ");
        switch (option) {
            // TODO Search methods below
            // case 1 -> searchAppsByName();
            // case 2 -> searchAppsByDeveloper(readValidDeveloperByName());
            // case 3 -> searchAppsEqualOrAboveAStarRating();
            // default -> System.out.println("Invalid option");
        }
    }

    //--------------------------------------------------
    // TODO UNCOMMENT THIS COMPLETED CODE as you start working through this class
    //--------------------------------------------------
    private void simulateRatings() {
        // simulate random ratings for all apps (to give data for recommended apps and reports etc).
        //if (appStoreAPI.numberOfApps() > 0) {
        //    System.out.println("Simulating ratings...");
        //    appStoreAPI.simulateRatings();
        //    System.out.println(appStoreAPI.listSummaryOfAllApps());
        //} else {
        //    System.out.println("No apps");
        //}
    }

    //--------------------------------------------------
    //  Persistence Menu Items
    //--------------------------------------------------

    private void saveAllData() {
        // TODO try-catch to save the developers to XML file
        // TODO try-catch to save the apps in the store to XML file
        try {
            appStoreAPI.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllData() {
        try {
            appStoreAPI.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO try-catch to load the developers from XML file
        // TODO try-catch to load the apps in the store from XML file
    }

}
