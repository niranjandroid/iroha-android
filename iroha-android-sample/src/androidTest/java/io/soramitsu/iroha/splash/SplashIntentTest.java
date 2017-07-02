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

package io.soramitsu.iroha.splash;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.soramitsu.iroha.view.activity.AccountRegisterActivity;
import io.soramitsu.iroha.view.activity.SplashActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static io.soramitsu.iroha.BaseTest.waitFor;

@LargeTest
public class SplashIntentTest {

    @Rule
    public IntentsTestRule<SplashActivity> mIntentRule = new IntentsTestRule<>(SplashActivity.class);
    private static final int SECONDS_TO_WAIT = 3;

    @After
    public void tearDown() {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }

    @Test
    public void timeout_IntentSent() throws Exception {
        onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(SECONDS_TO_WAIT)));
        intending(toPackage(AccountRegisterActivity.class.getName()));
    }
}
