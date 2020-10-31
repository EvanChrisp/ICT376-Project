package au.edu.murdoch.ict376project;
//Test Scenario 2
//One of three Scenarios simulating User use of application
//Scenario: User create's profile, fills in the data and then changes data

import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerActions.open;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ScenarioTest2 {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void Scenario() throws InterruptedException {

        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_login));
        onView(withId(R.id.clickToRegisterUserPwdButton)).check(matches(isDisplayed()));
        onView(withId(R.id.clickToRegisterUserPwdButton)).perform(click());
        Thread.sleep(200);

        onView(withId(R.id.usernameEt)).perform(typeText("ScenarioTester3"));
        onView(withId(R.id.passwordEt)).perform(typeText("Scenario3!"));
        onView(withId(R.id.rePasswordEt)).perform(typeText("Scenario3!"), closeSoftKeyboard());
        onView(withId(R.id.registerUserPwdButton)).perform(click());
        onView(withId(R.id.loginButton)).perform(click());
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("My profile")).perform(click());

        onView(withId(R.id.profileEditTextFname)).perform(typeText("Sin"), closeSoftKeyboard());
        onView(withId(R.id.profileEditTextLname)).perform(typeText("Tester"), closeSoftKeyboard());
        onView(withId(R.id.profileEditTextAddress)).perform(typeText("25 Fake Lane"), closeSoftKeyboard());
        onView(withId(R.id.profileEditTextPhone)).perform(typeText("0412345789"), closeSoftKeyboard());
        onView(withId(R.id.profileEditTextEmail)).perform(typeText("Sinny@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.profileSaveButton)).perform(click());

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("My profile")).perform(click());
        onView(withId(R.id.profileEditTextFname)).check(matches(withText("Sin")));

        onView(withId(R.id.profileEditTextFname)).perform(typeText("ario"),closeSoftKeyboard());
        onView(withId(R.id.profileSaveButton)).perform(click());

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("My profile")).perform(click());
        onView(withId(R.id.profileEditTextFname)).check(matches(withText("Sinario")));

    }
}
