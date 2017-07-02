/*
 * Copyright Soramitsu Co., Ltd. 2016 All Rights Reserved.
 * http://soramitsu.co.jp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.soramitsu.iroha.receive;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;

import io.soramitsu.iroha.BaseTest;
import io.soramitsu.iroha.R;
import io.soramitsu.iroha.view.activity.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@LargeTest
public class ReceiveTest extends BaseTest {

    private static final String BUNDLE_MAIN_ACTIVITY_KEY_UUID = "UUID";
    private static final String UUID = "4a9105579b2b4afd6d046a6c568cb1fd6659f60b26c2c1d2c7b491f4aa0a2d72";
    private static final String POCKET_MONEY = "100 IRH";
    private static final int THREAD_WAIT_TIME = 1000;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, MainActivity.class);
                    result.putExtra(BUNDLE_MAIN_ACTIVITY_KEY_UUID, UUID);
                    return result;
                }
            };

    @Test
    public void amount_DefaultAmount() throws Exception {
        sleep(THREAD_WAIT_TIME);
        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(withText(R.string.receive)));
        onView(withId(R.id.pocket_money))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isDisplayed()))
                .check(matches(withText(POCKET_MONEY)));
        onView(withId(R.id.receiver_amount))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isDisplayed()))
                .check(matches(withText("")));
        onView(withId(R.id.qr_code))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isDisplayed()));
        onView(withId(R.id.public_key))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isDisplayed()))
                .check(matches(not("")));
    }

    @Test
    public void bottomNav_MoveToWallet() throws Exception {
        onView(withId(R.id.action_wallet))
                .perform(click());
        sleep(THREAD_WAIT_TIME);
        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(withText(R.string.wallet)));
        onView(withId(R.id.pocket_money))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isDisplayed()))
                .check(matches(withText(POCKET_MONEY)));
    }

    @Test
    public void bottomNav_MoveToSend() throws Exception {
        onView(withId(R.id.action_sender))
                .perform(click());
        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(withText(R.string.send)));
        onView(withId(R.id.receiver))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isDisplayed()))
                .check(matches(withText("")));
        onView(withId(R.id.amount))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isDisplayed()))
                .check(matches(withText("")));
    }
}
