package com.instagram.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.instagram.R;
import com.instagram.adapter.HomeAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {

	private ListView listView;
	private ArrayList<ParseObject> postagens;
	private ArrayAdapter<ParseObject> adapter;

	private ParseQuery<ParseObject> query;

	public FragmentHome() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_home, container, false);

		listView = view.findViewById(R.id.home_listpostagem);

		postagens = new ArrayList<>();

		adapter = new HomeAdapter(getActivity(), postagens);

		listView.setAdapter(adapter);

		//Recupera as postagens
		getPostagens();

		return view;
	}

	private void getPostagens() {

		query = ParseQuery.getQuery("postagem");
		query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
		query.orderByDescending("createdAt");

		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {

				if (e == null){

					if (objects.size() > 0){

						postagens.clear();

						for (ParseObject object:  objects){
							postagens.add(object);
						}

						adapter.notifyDataSetChanged();
					}

				}
			}
		});

	}

}
