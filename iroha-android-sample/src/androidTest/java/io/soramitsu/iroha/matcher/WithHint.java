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

package io.soramitsu.iroha.matcher;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.espresso.intent.Checks.checkNotNull;
import static org.hamcrest.Matchers.is;

public class WithHint extends BoundedMatcher<View, EditText>{
    private final String expectedHint;

    private WithHint(String expectedHint) {
        super(EditText.class);
        this.expectedHint = expectedHint;
    }

    public static Matcher<View> withHint(final String hint) {
        final Matcher<String> stringMatcher = is(hint);
        checkNotNull(stringMatcher);
        return new WithHint(hint);
    }

    @Override
    protected boolean matchesSafely(EditText item) {
        return is(expectedHint).matches(item.getHint().toString());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with hint: ");
        is(expectedHint).describeTo(description);
    }
}
