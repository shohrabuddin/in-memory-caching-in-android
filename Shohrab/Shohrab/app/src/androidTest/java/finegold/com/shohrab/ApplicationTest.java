/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package finegold.com.shohrab;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import com.google.common.cache.LoadingCache;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import finegold.com.model.ContactModel;
import finegold.com.utility.Utility;
import static junit.framework.Assert.assertEquals;

/**
 * This class has to be tested under Test Artifact "Android Instrumentation Tests"
 * This class can be used to test phone numbers in different format and to test availability
 * of those phone numbers in a real device.
 * @author shohrab
 */

public class ApplicationTest {
    private Context instrumantationCtx;
    private LoadingCache<String, List<ContactModel>> contactCache;
    private List<ContactModel> mLstContact;
    private static final String[] PHONE_NUMBER={"+4915213779566","+49015213779566",
            "004915213779566", "015213779566",
            "+49 152 13779566", "0152 1377 9566"};

    @Before
    public void setup() {
        instrumantationCtx = InstrumentationRegistry.getTargetContext();
        contactCache = Utility.contactFromCache(instrumantationCtx);
    }

    /**
     * This method can be used to test phone number
     */
    @Test
    @SmallTest
    public void phoneNumberValidationTest (){
        boolean expected = true;
        for (int i=0;i<PHONE_NUMBER.length;i++){
            boolean actual = Utility.isPhoneNumberValid(PHONE_NUMBER[i]);

            assertEquals("Validating ".concat(PHONE_NUMBER[i]), expected, actual);
        }
    }

    /**This method can be used to check availability of any particular phone number in a device
     */
    @Test
    @MediumTest
    public void contactAvailabilityTest (){
        int expected =2;
        for (int i=0;i<PHONE_NUMBER.length;i++){
            mLstContact = contactCache.getUnchecked(PHONE_NUMBER[i]);
            int actual = mLstContact.size();
            assertEquals("Availability checking for ".concat(PHONE_NUMBER[i]), expected, actual);
        }

    }




}