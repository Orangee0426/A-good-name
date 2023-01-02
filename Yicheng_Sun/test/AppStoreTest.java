import controllers.AppStoreAPI;
import models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppStoreTest {
    private AppStoreAPI apps = new AppStoreAPI();

    private EducationApp edAppBelowBoundary, edAppOnBoundary;
    private Developer app1 = new Developer("app1", "www.app1.com");
    private Developer app2 = new Developer("app2", "www.app2.com");
    private Developer app3;
    private Rating eduR1 = new Rating(4,"edurating1","edu1");
    private Rating eduR2 = new Rating(4,"edurating2","edu2");

    private GameApp gamAppBelowBoundary, gamAppOnBoundary;
    private Developer gam1 = new Developer("gam1", "www.gam1.com");
    private Developer gam2 = new Developer("gam2", "www.gam2.com");
    private Rating gamR1 = new Rating(5,"gamR1","gam1");
    private Rating gamR2 = new Rating(5,"gamR1","gam2");

    private ProductivityApp proAppBelowBoundary, proAppOnBoundary;
    private Developer pro1 = new Developer("pro1", "www.pro1.com");
    private Developer pro2 = new Developer("pro2", "www.pro2.com");

    @BeforeEach
    void setUp() {
        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0), level(1-10).
        edAppBelowBoundary = new EducationApp(app1, "WeDo", 1, 1.0, 0, 1);
        edAppOnBoundary = new EducationApp(app2, "HHH", 1, 2.0, 0, 1);
        gamAppBelowBoundary = new GameApp(gam1, "EV3", 1001, 3.5, 2.99,true);
        gamAppOnBoundary = new GameApp(gam2, "", -1, 0, -1.00,true);
        proAppBelowBoundary = new ProductivityApp(pro1,"zzz", 100, 1.0, 0);
        proAppOnBoundary = new ProductivityApp(pro2,"zzz", 50, 1.0, 0);
    }

    @AfterEach
    void tearDown() {
        edAppBelowBoundary = edAppOnBoundary = null;
        gamAppBelowBoundary = gamAppOnBoundary = null;
        proAppBelowBoundary = proAppOnBoundary =null;
    }

    @Nested
    class TestNull{

        @Test
        void test01() {
            assertTrue(apps.addApp(edAppBelowBoundary));
            assertTrue(apps.addApp(edAppOnBoundary));
        }

        @Test
        void test02() {
            String s = apps.listAllApps();
            assertEquals("No apps",s);
            String s1 = apps.listSummaryOfAllApps();
            assertEquals("No apps",s1);

            String s2 = apps.listAllGameApps();
            assertEquals("No Game apps",s2);

            String s3 = apps.listAllEducationApps();
            assertEquals("No Education apps",s3);

            String s4 = apps.listAllProductivityApps();
            assertEquals("No Productivity apps",s4);

            String helo = apps.listAllAppsByName("helo");
            assertEquals("No apps for name helo exists",helo);

            String s5 = apps.listAllAppsAboveOrEqualAGivenStarRating(2);
            assertEquals("No apps have a rating of 2 or above",s5);

            String s6 = apps.listAllRecommendedApps();
            assertEquals("No recommended apps",s6);

            String s7 = apps.listAllAppsByChosenDeveloper(gam1);
            assertEquals("No apps for developer: "+gam1,s7);

            int i = apps.numberOfAppsByChosenDeveloper(gam1);
            assertEquals(0,i);


            App app = apps.deleteAppByIndex(0);
            assertEquals(null,app);

            apps.addApp(edAppBelowBoundary);
            App app1 = apps.deleteAppByIndex(0);
            assertEquals(edAppBelowBoundary,app1);

            App app4 = apps.randomApp();
            assertEquals(null,app4);

            apps.simulateRatings();

            boolean zzz = apps.isValidAppName("zzz");
            assertFalse(zzz);

            boolean validIndex = apps.isValidIndex(2);
            assertFalse(validIndex);

            apps.sortAppsByNameAscending();
            try {
                apps.load();
            } catch (Exception e) {

            }
            try {
                apps.save();
            } catch (Exception e) {

            }
        }
    }


    @Nested
    class TastData{

        @Test
        void test03(){
            apps.addApp(edAppBelowBoundary);
            String s = apps.listAllApps();
            assertTrue(s.contains("app1"));
            assertTrue(s.contains("WeDo"));


            apps.addApp(edAppOnBoundary);
            String s1 = apps.listSummaryOfAllApps();
            assertTrue(s1.contains("(www.app1.com)"));
            assertTrue(s1.contains("(www.app2.com)"));

            apps.addApp(gamAppBelowBoundary);
            apps.addApp(gamAppOnBoundary);
            String s2 = apps.listAllGameApps();
            assertTrue(s2.contains("(www.gam1.com)"));
            assertTrue(s2.contains("(www.gam2.com)"));

            apps.addApp(proAppBelowBoundary);
            apps.addApp(proAppOnBoundary);
            String s3 = apps.listAllProductivityApps();
            assertTrue(s3.contains("www.pro2.com"));
            assertTrue(s3.contains("www.pro1.com"));


            String s4 = apps.listAllEducationApps();
            assertTrue(s4.contains("(www.app1.com)"));
            assertTrue(s4.contains("(www.app2.com)"));


            String weDo = apps.listAllAppsByName("WeDo");
            assertTrue(weDo.contains("(www.app1.com)"));
            assertTrue(weDo.contains("WeDo"));

            String s5 = apps.listAllAppsAboveOrEqualAGivenStarRating(1);
            assertTrue(s5.contains("No apps have a rating of"));

            edAppBelowBoundary.addRating(eduR1);
            edAppBelowBoundary.addRating(eduR2);
            String s6 = apps.listAllAppsAboveOrEqualAGivenStarRating(1);
            assertTrue(s6.contains("(www.app1.com)"));
            assertTrue(s6.contains("edurating1"));

            // 必须大于3才能进行通通多的处理
            gamAppBelowBoundary.addRating(gamR1);
            gamAppOnBoundary.addRating(gamR2);
            gamAppBelowBoundary.setMultiplayer(true);
            String s7 = apps.listAllRecommendedApps();
            assertTrue(s7.contains("(www.gam2.com)"));
            assertTrue(s7.contains("gamR1"));

            String s8 = apps.listAllAppsByChosenDeveloper(app1);
            assertTrue(s8.contains("edu2"));

            int i = apps.numberOfAppsByChosenDeveloper(app1);
            assertEquals(1,i);
            App app = apps.randomApp();
            apps.simulateRatings();

            boolean weDo1 = apps.isValidAppName("WeDo");
            assertTrue(weDo1);

            App weDo2 = apps.getAppByName("WeDo");

            System.out.println(i);

            App appByIndex = apps.getAppByIndex(1);
            boolean validIndex = apps.isValidIndex(1);
            assertTrue(validIndex);

            apps.sortAppsByNameAscending();
        }
    }



}
