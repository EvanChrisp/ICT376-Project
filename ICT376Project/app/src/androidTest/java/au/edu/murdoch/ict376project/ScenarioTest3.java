package au.edu.murdoch.ict376project;
//Test Scenario 3
//One of three Scenarios simulating User use of application
//Scenario: User logs into application, looks up store with contact fragment, then places an order for an Xbox game

import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerActions.open;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class ScenarioTest3 {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void Scenario() throws InterruptedException {
        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_login));
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
        onView(withId(R.id.usernameEt)).perform(typeText("ScenarioTester2"));
        onView(withId(R.id.passwordEt)).perform(typeText("Scenario2!"), closeSoftKeyboard());;
        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_contact));
        onView(withId(R.id.gMaps)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.map)).check(matches(isDisplayed()));
        pressBack();

        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_xbox));
        onView(withId(R.id.XboxGamesID)).check(matches(isDisplayed()));

        onView(withId(R.id.xboxProductListView)).check(matches(isDisplayed()));
        onData(allOf()).inAdapterView(withId(R.id.xboxProductListView)).atPosition(0).perform(click());
        onView(withId(R.id.detailsCartButton)).perform(click());
        onView(withId(R.id.detailsCheckoutButton)).perform(click());
        onView(withId(R.id.checkoutPayment)).perform(click());

        onView(withId(R.id.card_name)).perform(typeText("Johnny Sins"), closeSoftKeyboard());
        onView(withId(R.id.card_number)).perform(typeText("4111111111111111"), closeSoftKeyboard());
        onView(withId(R.id.expiry_date)).perform(typeText("0524"), closeSoftKeyboard());
        onView(withId(R.id.cvc)).perform(typeText("939"), closeSoftKeyboard());
        onView(withId(R.id.btn_pay)).perform(click());

        onView(withId(R.id.imageViewPS)).check(matches(isDisplayed()));
    }
}
