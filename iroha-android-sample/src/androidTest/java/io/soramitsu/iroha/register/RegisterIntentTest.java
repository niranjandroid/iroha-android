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

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;

import io.soramitsu.iroha.BaseTest;
import io.soramitsu.iroha.R;
import io.soramitsu.iroha.view.activity.AccountRegisterActivity;
import io.soramitsu.iroha.view.activity.MainActivity;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
public class RegisterIntentTest extends BaseTest {

    private static final String USER_NAME = "iroha";
    private static final String BUNDLE_MAIN_ACTIVITY_KEY_UUID = "UUID";

    @Rule
    public IntentsTestRule<AccountRegisterActivity> mIntentRule
            = new IntentsTestRule<>(AccountRegisterActivity.class);

    @Test
    public void registerButton_IntentSent() throws Exception {
        final ViewInteraction usernameInteraction = onView(withId(R.id.user_name));
        usernameInteraction.perform(click());
        usernameInteraction.perform(typeText(USER_NAME));
        closeSoftKeyboard();
        onView(withId(R.id.register_button)).perform(click());
        intending(toPackage(MainActivity.class.getName()));
        intending(hasExtra(BUNDLE_MAIN_ACTIVITY_KEY_UUID, String.class));
        onView(withId(R.id.name)).check(matches(withText(USER_NAME)));
    }
}
