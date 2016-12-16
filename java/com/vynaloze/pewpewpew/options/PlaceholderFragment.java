package com.vynaloze.pewpewpew.options;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vynaloze.pewpewpew.R;


public class PlaceholderFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment, container, false);

        int pageNumber = getArguments().getInt(ARG_SECTION_NUMBER);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.image_fragment);
        TextView textView = (TextView) rootView.findViewById(R.id.text_fragment);

        switch (pageNumber) {
            case 1:
                imageView.setImageResource(R.drawable.aliens);
                textView.setText(R.string.enemy1);
                break;

            case 2:
                imageView.setImageResource(R.drawable.dishes);
                textView.setText(R.string.enemy2);
                break;

            case 3:
                imageView.setImageResource(R.drawable.christmas);
                textView.setText(R.string.enemy3);
                break;
        }
        return rootView;
    }
}