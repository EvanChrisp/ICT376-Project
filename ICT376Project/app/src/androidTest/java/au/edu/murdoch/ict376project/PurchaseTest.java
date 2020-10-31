package au.edu.murdoch.ict376project;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PurchaseTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void switchToFragment() {
        onView(withId(R.id.deal1)).perform(click());
        onView(withId(R.id.detailsCartButton)).perform(click());
        onView(withId(R.id.detailsCheckoutButton)).perform(click());
        onView(withId(R.id.checkoutPayment)).check(matches(isDisplayed()));
        onView(withId(R.id.checkoutPayment)).perform(click());

        onView(withId(R.id.card_name)).perform(typeText("Bob Test"), closeSoftKeyboard());
        onView(withId(R.id.card_number)).perform(typeText("5555555555554444"), closeSoftKeyboard());
        onView(withId(R.id.expiry_date)).perform(typeText("1224"), closeSoftKeyboard());
        onView(withId(R.id.cvc)).perform(typeText("345"), closeSoftKeyboard());
        onView(withId(R.id.btn_pay)).perform(click());

        onView(withId(R.id.imageViewPS)).check(matches(isDisplayed()));
    }

}
