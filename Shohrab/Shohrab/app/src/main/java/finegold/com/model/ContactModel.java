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

package finegold.com.model;

/**
 * This class can be used to model contact information
 * Created by shohrab.uddin on 01.02.2016.
 */
public class ContactModel {
    public String contactName, imageURL;

    /**
     * This is a constructor which takes two parameters
     * @param mContactName
     * @param mImageURL
     */
    public ContactModel(String mContactName, String mImageURL){
        this.contactName = mContactName;
        this.imageURL = mImageURL;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
