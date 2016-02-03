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

package finegold.com.utility;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.PhoneLookup;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import finegold.com.model.ContactModel;

/**
 * This class contains several utility methods which are being called throughout the application.
 * Created by shohrab.uddin on 01.02.2016.
 */
abstract public class Utility {
    private static Context mContext;
    /**
     * This method takes a phone number and if it is available in user's phone then
     * query contact name and image data
     * @param phoneNumber
     * @return List of ContactModel Objects
     */
    public static List<ContactModel> contactFromContentProvider(String phoneNumber){
        List <ContactModel> mListContact = new ArrayList<ContactModel>();
        String mContactName = null, mImageURI = null;
        ContentResolver mContextResolver = mContext.getContentResolver();

        Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor mCursor = mContextResolver.query(uri, new String[]{PhoneLookup.DISPLAY_NAME,PhoneLookup.PHOTO_URI },
                null, null, null);

        if (mCursor == null) return null;

        if(mCursor.moveToFirst()) {
            do{
                mContactName = mCursor.getString(mCursor.getColumnIndex(PhoneLookup.DISPLAY_NAME));
                mImageURI = mCursor.getString(mCursor.getColumnIndex(PhoneLookup.PHOTO_URI));
                mListContact.add(new ContactModel(mContactName, (mImageURI==null ? "/" : mImageURI)));
            }while (mCursor.moveToNext());
        }

        if(mCursor != null && !mCursor.isClosed()) mCursor.close();

        return mListContact;
    }

    /**
     * This method provides contact data from the cache
     * CacheLoader is invoked only if data is not available in cache otherwise data is loaded from LoadingCache
     */
    public static LoadingCache<String, List<ContactModel>> contactFromCache(Context context){
        mContext = context;
        LoadingCache<String, List<ContactModel>> contactCache = CacheBuilder.newBuilder()
                .maximumSize(100) // maximum 100 records can be cached
                .expireAfterAccess(7, TimeUnit.DAYS) // cache will expire after one week of access
                .build(new CacheLoader<String, List<ContactModel>>() {

                    @Override
                    public List<ContactModel> load(String mPhoneNumber) throws ExecutionException, IOException {
                        //make the expensive call
                        return contactFromContentProvider(mPhoneNumber);
                    }
                });
        return contactCache;
    }

    /**
     * This method is used to validate phone numbers
     * @param phoneNumber
     * @return true is phone number is valid otherwise false
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {

        String regex = "^\\+?[0-9. ()-]{10,25}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        if (matcher.matches()) return true;
        else return false;
    }

}
