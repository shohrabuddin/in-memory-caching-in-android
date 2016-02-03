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

package finegold.com.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.cache.LoadingCache;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import finegold.com.adapter.ContactListViewAdapter;
import finegold.com.model.ContactModel;
import finegold.com.shohrab.R;
import finegold.com.utility.Utility;

/**
 * This is the launcher activity class. This class can be used to take a phone number from user and if
 * that number exists in the contact then it shows the contact name and image in a list view
 * Created by shohrab.uddin on 01.02.2016.
 */
public class MainActivity extends AppCompatActivity {
    public static final int CURRENT_ANDROID_VERSION = android.os.Build.VERSION.SDK_INT;
    public static final int CONTACT_REQUEST_CODE = 110;
    public static Context sContext;
    private List<ContactModel> mLstContact;
    private String mMobileNumber = null;
    private ContactListViewAdapter mAdapter;
    private LoadingCache<String, List<ContactModel>> contactCache;

    //@InjectView is ButterKnife annotation which helps to get rid of annoying findViewByID
    @InjectView(R.id.activity_main_coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @InjectView(R.id.activity_main_edtPhoneNumber)
    EditText mEdtTextPhoneNumber;
    @InjectView(R.id.activity_main_txtContactList)
    TextView mTxtViewContactList;
    @InjectView(R.id.activity_main_lstContactList)
    ListView mLstViewContact;
    @InjectView(R.id.activity_main_BtnShowContact)
    Button mBtnShowContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Before using any views injection on the ButterKnife object has to be made
        ButterKnife.inject(this);

        sContext = getApplicationContext();
        contactCache = Utility.contactFromCache(getApplicationContext());
        mTxtViewContactList.setVisibility(View.GONE);
    }

    /**
     * This method is used to invoke the utility method which returns the available
     * contacts of a particular phone number
     */
    private void showContacts(){
        mMobileNumber = mEdtTextPhoneNumber.getText().toString();
        if(Utility.isPhoneNumberValid(mMobileNumber)){

            //getUnchecked method of LoadingCache class can verify whether the requested key (here mMobileNumber)
            // is already exist in the cache or not and react accordingly
            mLstContact = contactCache.getUnchecked(mMobileNumber);

            mAdapter = new ContactListViewAdapter(MainActivity.this, mLstContact);
            mLstViewContact.setAdapter(mAdapter);

            if(mLstContact.size()>0){
                mTxtViewContactList.setVisibility(View.VISIBLE);
            }else{
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "This phone number is not available", Snackbar.LENGTH_LONG);
                snackbar.show();
                mTxtViewContactList.setVisibility(View.GONE);
            }

        }else{
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Phone number is invalid", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    /**
     *  This method is invoked to get the READ_CONTACTS Permission at run time
     */
    @OnClick(R.id.activity_main_BtnShowContact) //@OnClick is a Butterknife annotation. I can handle click event of a view
    public void checkPermission(){

        if (CURRENT_ANDROID_VERSION >= Build.VERSION_CODES.M) { //Marshmallow

            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {

                // if user denied to access the permission then show a custom message to make him/her understand why the app is asking this
                // permission.
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        Manifest.permission.READ_CONTACTS)) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle(R.string.permisstion_title_contact);
                    alertDialogBuilder.setMessage(R.string.permission_desc_contact);

                    alertDialogBuilder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialogBuilder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });

                } else {// If user never denied to access the permission then no explanation needed, we can directly request the permission.
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS},
                            CONTACT_REQUEST_CODE);
                }
            }else{ //Permission is already granted by the user
                showContacts();
            }
        }else{ //IF android version is lower than 23
            showContacts();
        }

    }

    //When the user responds on providing permission, the system invokes the app's onRequestPermissionsResult()
    //method, passing it the user response.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case CONTACT_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    showContacts();

                } else {
                    Toast.makeText(MainActivity.this,"Permission is Denied!",Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

}
