package com.jhony.jester.play;

import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jhony.jester.play.Activitys.SplashActivity;
import com.jhony.jester.play.Connections.EverythingNearby;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by JAR on 17-01-2018.
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class EverythingnearbyTest {
    @Rule
    public ActivityTestRule<SplashActivity> activityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void clickHost() {
        onView(withId(R.id.host_tv)).perform(click());
    }


}
