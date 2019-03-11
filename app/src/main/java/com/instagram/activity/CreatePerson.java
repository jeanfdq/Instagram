package com.instagram.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instagram.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.instagram.util.CodeEncodeBase64;
import com.instagram.util.ValidaEmail;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class CreatePerson extends AppCompatActivity {

	private EditText edtFirstNome, edtLastNome, edtEmail, edtPhone, edtSenha;
	private Button btnCadastrar;
	private TextView tvLogin;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_person);

		//Mapeamento dos campos--------------------------------------
		edtFirstNome= findViewById(R.id.createperson_edt_Firstname);
		edtLastNome = findViewById(R.id.createperson_edt_Lastname);
		edtEmail    = findViewById(R.id.createperson_edt_email);
		edtPhone    = findViewById(R.id.createperson_edt_phonenumber);
		edtSenha    = findViewById(R.id.createperson_edt_password);

		btnCadastrar= findViewById(R.id.createperson_btn_cad);

		tvLogin     = findViewById(R.id.createperson_tv_login);
		//-----------------------------------------------------------

		//Colocando máscara no telefone------------------------------
		SimpleMaskFormatter maskPhone = new SimpleMaskFormatter("+NN (NN) N NNNN-NNNN");
		MaskTextWatcher mtwTelefone = new MaskTextWatcher(edtPhone, maskPhone);
		edtPhone.addTextChangedListener(mtwTelefone);
		//-----------------------------------------------------------

		btnCadastrar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean IsCadastroOK = cadastraPessoa();
				if (IsCadastroOK){
					startActivity(new Intent(CreatePerson.this, MainActivity.class));
					finish();
				}
			}
		});

		tvLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openLogin();
			}
		});


	}

	private boolean cadastraPessoa(){

		String firstName= edtFirstNome.getText().toString().trim();
		String lastName = edtLastNome.getText().toString().trim();
		String email    = edtEmail.getText().toString().trim();
		String phone    = edtPhone.getText().toString().trim();
		String senha    = edtSenha.getText().toString().trim();
		boolean IsErro  = false;

		if (firstName.isEmpty()){
			edtFirstNome.setError("Informe seu nome!");
			IsErro = true;
		}
		if (firstName.length()<2){
			edtFirstNome.setError("Primeiro nome inválido!");
			IsErro = true;
		}
		if (lastName.isEmpty()){
			edtLastNome.setError("Informe seu Sobrenome!");
			IsErro = true;
		}
		if (lastName.length()<2){
			edtLastNome.setError("Sobrenome nome inválido!");
			IsErro = true;
		}

		if (email.isEmpty()){
			edtEmail.setError("Informe seu e-mail!");
			IsErro = true;
		}

		if (phone.isEmpty()){
			edtPhone.setError("Informe seu celular!");
			IsErro = true;
		}

		boolean IsEmailValido = false;
		IsEmailValido = ValidaEmail.validar(email);
		if (!IsEmailValido){
			edtEmail.setError("E-mail inválido");
			IsErro = true;
		}

		if (senha.isEmpty() || senha.length()<6){
			edtSenha.setError("Informe uma senha de 6 dígitos.");
			IsErro = true;
		}

		if (!IsErro){

			String CompleteName = firstName+" "+lastName;

			ParseUser user = new ParseUser();

			String senhaCript = CodeEncodeBase64.Encode64(senha);
			user.put("firstname",firstName);
			user.put("lastname",lastName);
			user.put("completename",CompleteName);
			user.put("phonenumber",phone.replace(" ","").replace("(","").replace(")","").replace("-",""));
			user.setEmail(email);
			user.setUsername(email);
			user.setPassword(senhaCript);
			user.signUpInBackground(new SignUpCallback() {
				@Override
				public void done(ParseException e) {
					if (e == null){

						Toast.makeText(CreatePerson.this, "Usuário cadastrado com sucesso!",Toast.LENGTH_LONG).show();

					}else{
						Toast.makeText(CreatePerson.this, "Erro: " + e.getMessage(),Toast.LENGTH_LONG).show();
					}
				}
			});

			return true;
		}else{
			return false;
		}

	}

	private void openLogin() {
		startActivity(new Intent(CreatePerson.this, Login.class));
		finish();
	}
}