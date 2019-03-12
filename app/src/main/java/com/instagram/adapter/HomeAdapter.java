package com.instagram.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.instagram.R;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends ArrayAdapter<ParseObject> {
	
	private Context context;
	private ArrayList<ParseObject> postagens;

	public HomeAdapter(Context c, ArrayList<ParseObject> objects) {
		super(c, 0, objects);

		this.context = c;
		this.postagens = objects;

	}

	
	@Override
	public View getView(int position,  View convertView, ViewGroup parent) {

		View view = convertView;

		//Vamos verificar se a view esta criada, pois o android armazena no cache a view
		if (view == null){

			LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.lista_postagem,parent,false);

		}

		if (postagens.size() > 0){

			ImageView imagemPostagem = view.findViewById(R.id.image_postagem);

			ParseObject parseObject = postagens.get(position);

//			parseObject.getParseFile("photo");
			Picasso.get().load(parseObject.getParseFile("photo").getUrl()).into(imagemPostagem);

		}
		return view;
	}
}
