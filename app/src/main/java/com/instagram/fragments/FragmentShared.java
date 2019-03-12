package com.instagram.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.instagram.R;
import com.instagram.model.UserSharePhoto;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.instagram.util.getCurrentDataTime;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentShared extends Fragment {


	public FragmentShared() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_shared, container, false);

		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, 1);

		return view;

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_CANCELED){

			Toast.makeText(getActivity(), "Nenhuma foto selecionada!",Toast.LENGTH_LONG).show();

			Fragment fragment = new FragmentHome();
			getActivity().getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.main_frame, fragment)
					.addToBackStack(null)
					.commit();

		}

		if (resultCode == RESULT_OK){

			if (requestCode == 1 && data != null){

				//Recupera o identificador do retorno dos dados
				Uri identifierImage = data.getData();

				try{

					Bitmap imagem = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), identifierImage);

					//Comprimi a imagem em PNG
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					imagem.compress(Bitmap.CompressFormat.PNG, 80, stream);

					//Cria um array de buytes da imagem para o ParseFile
					byte[] byteArray = stream.toByteArray();

					//Transforma a imagem em arquivo especifico do PARSE
					SimpleDateFormat dateFormat = new SimpleDateFormat("ddmmyyyyhhmmss");
					String nameImage = "post_" + ParseUser.getCurrentUser().getUsername().trim()+"_"+ dateFormat.format(new Date()) + ".png";
					ParseFile parseFile = new ParseFile(nameImage, byteArray);


					//Pega o user Current
					ParseUser userParse = ParseUser.getCurrentUser();

					UserSharePhoto sharePhoto = new UserSharePhoto();
					sharePhoto.setUsername(userParse.getUsername().trim());
					sharePhoto.setUserfullname(userParse.get("completename").toString().trim());
					sharePhoto.setPostdata(getCurrentDataTime.getDateTime());
					sharePhoto.setPhoto(parseFile);
					sharePhoto.PostPhoto(getActivity());

				}catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

	}
}
