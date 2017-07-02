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

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.List;

public class WithDrawable extends TypeSafeMatcher<View> {
    private final int expectedId;
    private String resourceName;

    private WithDrawable(int resourceId) {
        this.expectedId = resourceId;
    }

    public static Matcher<View> withDrawable(@DrawableRes int resourceId) {
        return new WithDrawable(resourceId);
    }

    @Override
    protected boolean matchesSafely(View item) {
        if (!(item instanceof ImageView || item instanceof TextView)) {
            return false;
        }

        List<Drawable> checks = new ArrayList<>(4);
        if (item instanceof ImageView) {
            Drawable actual = ((ImageView) item).getDrawable();
            if (actual != null) {
                checks.add(actual);
            }
        } else {
            for (Drawable d : ((TextView) item).getCompoundDrawables()) {
                if (d != null) {
                    checks.add(d);
                }
            }
        }

        Resources res = item.getContext().getResources();
        Drawable expectedDrawable = res.getDrawable(expectedId);

        if (checks.size() == 0 || expectedDrawable == null) {
            return false;
        }

        resourceName = res.getResourceName(expectedId);

        for (Drawable actualDrawable : checks) {
            Rect bounds = new Rect(0, 0, 64, 64);
            expectedDrawable.setBounds(bounds);
            actualDrawable.setBounds(bounds);

            Bitmap bm1 = drawDrawableToBitmap(expectedDrawable);
            Bitmap bm2 = drawDrawableToBitmap(actualDrawable);

            if (bm1.sameAs(bm2)) {
                return true;
            }
        }

        return false;
    }

    private Bitmap drawDrawableToBitmap(Drawable drawable) {
        Rect bounds = drawable.getBounds();
        Bitmap bmp = Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        drawable.draw(canvas);
        return bmp;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with drawable resource: " + resourceName);
    }
}
