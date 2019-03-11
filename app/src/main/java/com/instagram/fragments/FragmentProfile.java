package com.instagram.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.instagram.R;
import com.instagram.activity.Login;
import com.instagram.activity.MainActivity;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment {


	private Toolbar toolbar;
	private ImageButton btnLogout;

	public FragmentProfile() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_profile, container, false);


		//Toolbar-----------------------------------------------------------
		toolbar = view.findViewById(R.id.profile_toolbar);
		toolbar.setContentInsetsAbsolute(0,0);
		toolbar.setLogo(R.drawable.ic_account_circle_black_24dp);
		toolbar.setTitleTextColor(getContext().getColor(R.color.colorBlack));
		toolbar.setTitle(ParseUser.getCurrentUser().getUsername());
		toolbar.setPadding(20,0,20,0);
		AppCompatActivity activity = (AppCompatActivity)getActivity();
		activity.setSupportActionBar(toolbar);
		//------------------------------------------------------------------

		//Buttom Logout
		btnLogout = view.findViewById(R.id.profile_logout);
		btnLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ParseUser.logOut();
				startActivity(new Intent(getActivity(), Login.class));
				getActivity().finish();
			}
		});

		return view;
	}

}
