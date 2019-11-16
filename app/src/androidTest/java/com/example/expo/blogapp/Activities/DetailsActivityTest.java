package com.example.expo.blogapp.Activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.expo.blogapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DetailsActivityTest {

    @Rule
    public ActivityTestRule<Upiactivity> mActivityTestRule = new ActivityTestRule<Upiactivity>(Upiactivity.class);

    @Test
    public void splashScreenActivityTest() {




        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.amount12),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.amount),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.userphone1),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.userphone),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("6284224993"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.username2),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.username1),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("harish"), closeSoftKeyboard());


        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.donate), withText("Donate"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                        1),
                                6),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction cardView = onView(
                allOf(withId(R.id.paytm1),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                0)));
        cardView.perform(scrollTo(), click());

        pressBack();
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
