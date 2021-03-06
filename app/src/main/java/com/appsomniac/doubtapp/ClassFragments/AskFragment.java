package com.appsomniac.doubtapp.ClassFragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appsomniac.doubtapp.R;
public class AskFragment extends Fragment {

    View listItemView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        listItemView = inflater.inflate(R.layout.item_channels, container, false);
        return listItemView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        //public TextView name;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_channels, parent, false));
           // picture = (ImageView) itemView.findViewById(R.id.tile_picture);
            //name = (TextView) itemView.findViewById(R.id.tile_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        // Set numbers of Tiles in RecyclerView.
        private static final int LENGTH = 16;

       // private final String[] channelsName;
       // private final Drawable[] channelsPictures;
        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
          //  channelsName = resources.getStringArray(R.array.places);
//            TypedArray a = resources.obtainTypedArray(R.array.places_picture);
//            channelsPictures = new Drawable[a.length()];
//            for (int i = 0; i < channelsPictures.length; i++) {
//                channelsPictures[i] = a.getDrawable(i);
//            }
           // a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
          //  holder.picture.setImageDrawable(channelsPictures[position % channelsPictures.length]);
          //  holder.name.setText(channelsName[position % channelsName.length]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}