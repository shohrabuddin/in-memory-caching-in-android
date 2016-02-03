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

package finegold.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import finegold.com.model.ContactModel;
import finegold.com.shohrab.R;

/**
 * This class can be used to show the contact information in a list view
 * Google GUAVA API is used to implement in-memory caching feature
 * Created by shohrab.uddin on 01.02.2016.
 */
public class ContactListViewAdapter extends BaseAdapter {

    private List<ContactModel> mContactList ;
    private Context mContext ;

    /**
     * This is a constructor which is used to initialize context and mContactList
     * @param context
     * @param contacts
     */
    public ContactListViewAdapter(Context context, List<ContactModel> contacts) {
        //super(context, R.layout.layout_list_view, contacts);
        this.mContext = context ;
        this.mContactList = contacts ;
    }

    @Override
    public int getCount() {
        return mContactList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.layout_list_view, parent, false);

        holder = new ViewHolder(rowView);

        holder.contactTextView.setText(mContactList.get(position).getContactName());
        Picasso.with(mContext).invalidate(mContactList.get(position).getImageURL());
        Picasso.with(mContext).load(mContactList.get(position).getImageURL())
                .error(R.mipmap.ic_launcher)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .resize(130, 130)
                .into(holder.contactImageView);
        return rowView;
    }

    /**
     * This inner class is used to inject views using ButterKnife
     */
    static class ViewHolder{
        @InjectView(R.id.layout_list_view_imgContact)
        ImageView contactImageView;
        @InjectView(R.id.layout_list_view_txtContact)
        TextView contactTextView;

        public ViewHolder(View view){
            ButterKnife.inject(this, view);
        }
    }
}
