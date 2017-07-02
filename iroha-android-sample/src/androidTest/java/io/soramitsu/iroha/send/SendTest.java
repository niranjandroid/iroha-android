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

package io.soramitsu.iroha.send;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.soramitsu.iroha.BaseTest;
import io.soramitsu.iroha.R;
import io.soramitsu.iroha.view.activity.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@LargeTest
public class SendTest extends BaseTest {

    private static final String BUNDLE_MAIN_ACTIVITY_KEY_UUID = "UUID";
    private static final String UUID = "4a9105579b2b4afd6d046a6c568cb1fd6659f60b26c2c1d2c7b491f4aa0a2d72";
    private static final String POCKET_MONEY = "100 IRH";
    private static final int THREAD_WAIT_TIME = 1000;
    private static final String NUMBER = "1234";
    private static final String NOT_NUMBER = "abcdefg";
    private static final String RECEIVER_ADDRESS = "send_to";

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


    @Before
    public void before() {
        onView(withId(R.id.action_sender)).perform(click());
    }

    @Test
    public void to_NotFocusable() throws Exception {
        onView(withId(R.id.receiver))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(not(isFocusable())));
    }

    @Test
    public void amount_DefaultAmount() throws Exception {
        sleep(THREAD_WAIT_TIME);
        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(withText(R.string.send)));
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

    @Test
    public void amount_Focusable() throws Exception {
        onView(withId(R.id.amount))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isFocusable()));
    }

    @Test
    public void amount_InputNumber() throws Exception {
        onView(withId(R.id.amount))
                .perform(typeText(NUMBER))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(withText(NUMBER)));
    }

    @Test
    public void amount_InputNotNumber() throws Exception {
        onView(withId(R.id.amount))
                .perform(typeText(NOT_NUMBER))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(withText("")));
    }

    @Test
    public void sendButton_NotInputReceiverAddressAndAmount() throws Exception {
        onView(withId(R.id.submit_button))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isEnabled()))
                .perform(click());
        onView(withId(R.id.title_text))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(withText(R.string.warning)));
    }

    @Test
    public void sendButton_OnlyInputReceiverAddress() throws Exception {
        onView(withId(R.id.receiver))
                .perform(typeText(RECEIVER_ADDRESS));
        onView(withId(R.id.submit_button))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isEnabled()))
                .perform(click());
        onView(withId(R.id.title_text))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(withText(R.string.warning)));
    }

    @Test
    public void sendButton_OnlyInputAmount() throws Exception {
        onView(withId(R.id.amount))
                .perform(typeText(NUMBER));
        onView(withId(R.id.submit_button))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isEnabled()))
                .perform(click());
        onView(withId(R.id.title_text))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(withText(R.string.warning)));
    }

    @Test
    public void bottomNav_MoveToReceive() throws Exception {
        onView(withId(R.id.action_receipt))
                .perform(click());
        sleep(THREAD_WAIT_TIME);
        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(withText(R.string.receive)));
        onView(withId(R.id.pocket_money))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isDisplayed()))
                .check(matches(withText(POCKET_MONEY)));
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
}
