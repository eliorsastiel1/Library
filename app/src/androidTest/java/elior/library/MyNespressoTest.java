package elior.library;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MyNespressoTest {
    @Rule
    public ActivityTestRule<MainActivity> loginActivity =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void AreInstructionsGood() {
        Espresso.onView(withText("Email")).check(matches(isDisplayed()));
        Espresso.onView(withText("Password")).check(matches(isDisplayed()));
    }
}