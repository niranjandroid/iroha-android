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

package io.soramitsu.iroha.register;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import io.soramitsu.iroha.BaseTest;
import io.soramitsu.iroha.R;
import io.soramitsu.iroha.view.activity.AccountRegisterActivity;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
public class RegisterTest extends BaseTest {

    private static final String USER_NAME_LENGTH_LESS_THAN_10 = "iroha";
    private static final String USER_NAME_LENGTH_JUST_10 = "iroha_andr";
    private static final String USER_NAME_LENGTH_MORE_THAN_10 = "iroha_android";

    @Rule
    public ActivityTestRule<AccountRegisterActivity> mActivityRule
            = new ActivityTestRule<>(AccountRegisterActivity.class);

    @Test
    public void registerButton_UsernameNotInput() throws Exception {
        onView(withId(R.id.register_button))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(isEnabled()))
                .check(matches(withText(R.string.register)))
                .perform(click());
        onView(withId(R.id.title_text))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(withText(R.string.warning)));
    }

    @Test
    public void username_InitialState() throws Exception {
        onView(withId(R.id.user_name))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(hasNoErrorText()))
                .check(matches(withText("")));
    }

    @Test
    public void username_LengthLessThan10() throws Exception {
        onView(withId(R.id.user_name))
                .perform(click(), typeText(USER_NAME_LENGTH_LESS_THAN_10))
                .check(matches(isFocusable()));
        closeSoftKeyboard();
        onView(withId(R.id.user_name))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(withText(USER_NAME_LENGTH_LESS_THAN_10)));
    }

    @Test
    public void username_LengthJust10() throws Exception {
        onView(withId(R.id.user_name))
                .perform(click(), typeText(USER_NAME_LENGTH_JUST_10))
                .check(matches(isFocusable()));
        closeSoftKeyboard();
        onView(withId(R.id.user_name))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(withText(USER_NAME_LENGTH_JUST_10)));
    }

    @Test
    public void username_LengthMoreThan10() throws Exception {
        onView(withId(R.id.user_name))
                .perform(click(), typeText(USER_NAME_LENGTH_MORE_THAN_10))
                .check(matches(isFocusable()));
        closeSoftKeyboard();
        onView(withId(R.id.user_name))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(withText(USER_NAME_LENGTH_JUST_10)));
    }
}
