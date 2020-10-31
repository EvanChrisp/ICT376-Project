package au.edu.murdoch.ict376project;
//Test Scenario 1
//One of three Scenarios simulating User use of application
//Scenario: Anonymous User.User checks the Account but finds they have to login in.Then User purchases two games, one ps4 and one switch game, using the nav bar, and checkouts.

import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerActions.open;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class ScenarioTest1 {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void Scenario() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("My profile")).perform(click());
        onView(withId(R.id.profileTextViewUname)).check(matches(withText(R.string.please_login)));
        onView(withContentDescription(R.string.nav_app_bar_navigate_up_description)).perform(click());

        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_playstation));
        onView(withId(R.id.playstationProductListView)).check(matches(isDisplayed()));
        onData(allOf()).inAdapterView(withId(R.id.playstationProductListView)).atPosition(0).perform(click());

        onView(withId(R.id.detailsCartButton)).perform(click());
        onView(withContentDescription(R.string.nav_app_bar_navigate_up_description)).perform(click());
        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_nintendo));
        onView(withId(R.id.nintendoProductListView)).check(matches(isDisplayed()));
        onData(allOf()).inAdapterView(withId(R.id.nintendoProductListView)).atPosition(0).perform(click());
        onView(withId(R.id.detailsCartButton)).perform(click());
        onView(withId(R.id.detailsCheckoutButton)).perform(click());
        onView(withId(R.id.checkoutPayment)).perform(click());

        onView(withId(R.id.card_name)).perform(typeText("Bob Bobbington"), closeSoftKeyboard());
        onView(withId(R.id.card_number)).perform(typeText("5555555555554444"), closeSoftKeyboard());
        onView(withId(R.id.expiry_date)).perform(typeText("1225"), closeSoftKeyboard());
        onView(withId(R.id.cvc)).perform(typeText("506"), closeSoftKeyboard());
        onView(withId(R.id.btn_pay)).perform(click());

        onView(withId(R.id.imageViewPS)).check(matches(isDisplayed()));
    }
}
