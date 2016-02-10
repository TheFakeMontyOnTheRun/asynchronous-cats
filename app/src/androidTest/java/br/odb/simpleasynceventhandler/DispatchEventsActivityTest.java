package br.odb.simpleasynceventhandler;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.ImageView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

/**
 * Created by monty on 09/02/16.
 *
 * Interface tests can be fragile. I'm still researching how to make those stronger.
 *
 *
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class DispatchEventsActivityTest {

    //http://hitherejoe.com/testing-imageview-changes-android-espresso-automated-tests/
    public static Matcher doesImagesContainTheSamePixels( final Drawable drawable ) {
        return new BoundedMatcher(ImageView.class) {


            @Override
            public void describeTo(Description description) {
                description.appendText("is image the same as: ");
                description.appendValue(drawable);
            }

            @Override
            protected boolean matchesSafely(Object item) {
                ImageView view = (ImageView) item;
                Bitmap bitmapCompare = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Drawable drawable = view.getDrawable();
                Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                return bitmapCompare.sameAs(bitmap);
            }
        };
    };

    @Rule
    public ActivityTestRule<DispatchEventsActivity> mActivityRule = new ActivityTestRule<>(
            DispatchEventsActivity.class);

    @Test
    public void verifyClickingOnButtonWillActuallyChangeTheImage() throws InterruptedException {

        ImageView catView1 = ((ImageView)mActivityRule.getActivity().findViewById( R.id.catImageView1) );

        //load some image first
        onView(withId(R.id.btnLoadCatImage1)).perform(click());

        Thread.sleep(3000L);

        Drawable originalImage = catView1.getDrawable();

        onView(withId(R.id.catImageView1))
                .check(matches(doesImagesContainTheSamePixels(originalImage)));

        onView(withId(R.id.btnLoadCatImage1)).perform(click());

        Thread.sleep(3000L);

        onView(withId(R.id.catImageView1))
                .check(matches(not( doesImagesContainTheSamePixels(originalImage))));
    }
}
