package com.instagram.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.instagram.R;
import com.instagram.activity.Login;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment {


	private Toolbar toolbar;
	private CircleImageView imageProfile;
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
		toolbar.setContentInsetsAbsolute(0, 0);
		toolbar.setLogo(R.drawable.ic_account_circle_black_24dp);
		toolbar.setTitleTextColor(getContext().getColor(R.color.colorBlack));
		toolbar.setTitle(ParseUser.getCurrentUser().get("completename").toString().trim());
		toolbar.setPadding(20, 0, 20, 0);
		AppCompatActivity activity = (AppCompatActivity) getActivity();
		activity.setSupportActionBar(toolbar);
		//------------------------------------------------------------------

		//Image Profile
		imageProfile = view.findViewById(R.id.profile_imgProfile);
		imageProfile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, 1);
			}
		});

		//Carregando a imagem do Profile
		loadPhotoProfile();

		//Buttom Logout
		btnLogout = view.findViewById(R.id.profile_logout);
		btnLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.ThemeAlertLogout);
				dialog.setTitle("Logout Instagram");
				dialog.setIcon(R.drawable.ic_account_circle_black_24dp);
				dialog.setMessage("Deseja realizar o logout?");

				dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						logoutUser();
					}
				});

				dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});

				dialog.create();
				dialog.show();

			}
		});

		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);


		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(getContext(), "Nenhuma imagem selecionada!", Toast.LENGTH_LONG).show();
		}

		if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

			//Recupera o identificador do retorno dos dados
			Uri identifierImage = data.getData();

			//recupra a imagem selecionada
			try {

				Bitmap imagem = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), identifierImage);

				//Comprimi a imagem em PNG
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				imagem.compress(Bitmap.CompressFormat.PNG, 80, stream);

				//Cria um array de buytes da imagem para o ParseFile
				byte[] byteArray = stream.toByteArray();

				//Transforma a imagem em arquivo especifico do PARSE
				SimpleDateFormat dateFormat = new SimpleDateFormat("ddmmyyyyhhmmss");
				String nameImage = "profile_" + dateFormat.format(new Date()) + ".png";
				ParseFile parseFile = new ParseFile(nameImage, byteArray);

				//Salvando o bjeto do Parse
				ParseUser user = ParseUser.getCurrentUser();
				user.put("photo", parseFile);
				user.put("phonenumber", "123445");
				user.save();

				loadPhotoProfile();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

	}

	private void loadPhotoProfile() {
		ParseUser user = ParseUser.getCurrentUser();
		ParseFile thumbail = user.getParseFile("photo");
		if (thumbail != null) {

			thumbail.getDataInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					if (e == null) {
						Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
						imageProfile.setImageBitmap(bmp);
					}
				}
			});
		}else{
			imageProfile.setImageDrawable(getActivity().getDrawable(R.drawable.round_account_circle_black_48dp));
		}
	}

	private void logoutUser() {
		ParseUser.logOut();
		startActivity(new Intent(getActivity(), Login.class));
		getActivity().finish();
	}

}
