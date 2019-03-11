package com.instagram.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instagram.R;
import com.instagram.util.CodeEncodeBase64;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends AppCompatActivity {

	private EditText edtEmail, edtSenha;
	private TextView txvForgot, txvCreate;
	private Button btnLogin;

	private AnimationDrawable animationDrawable;
	private RelativeLayout relativeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		verificaUsuarioLogado();

		relativeLayout = findViewById(R.id.relativeLogin);

		edtEmail = findViewById(R.id.login_ed_login);
		edtSenha = findViewById(R.id.login_ed_senha);
		btnLogin = findViewById(R.id.login_btn_login);
		txvForgot = findViewById(R.id.login_tv_forgot_pass);
		txvCreate = findViewById(R.id.login_tv_create);

		//Habilitando os clicks
		txvCreate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openCreateUser();
			}
		});

		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				boolean IsErr = false;

				if (edtEmail.getText().toString().isEmpty()){
					edtEmail.setError("Informe o e-mail!");
					IsErr = true;
				}
				if (edtSenha.getText().toString().isEmpty()){
					edtSenha.setError("Informe sua senha!");
					IsErr = true;
				}

				if (!IsErr)
					loginUsuario();
			}
		});


		animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
		animationDrawable.setEnterFadeDuration(2000);
		animationDrawable.setExitFadeDuration(1000);
		animationDrawable.start();

	}

	private void openCreateUser() {
		Intent intent = new Intent(Login.this, CreatePerson.class);
		startActivity(intent);
		finish();
	}

	private void verificaUsuarioLogado() {

		if (ParseUser.getCurrentUser() != null) {
			startActivity(new Intent(Login.this, MainActivity.class));
			finish();
		}

	}

	private boolean loginUsuario() {

		String userEmail = edtEmail.getText().toString().trim();
		String userSenha = edtSenha.getText().toString().trim();
		String senhaCode64 = CodeEncodeBase64.Encode64(userSenha);

		ParseUser.logInInBackground(userEmail, senhaCode64, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException e) {
				if (e == null) {
					verificaUsuarioLogado();
				} else {
					Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});

		return true;
	}

}
