package com.instagram.classes;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

public class StarterApplication extends Application {


	@Override
	public void onCreate() {
		super.onCreate();


		//Comando para instalar o dashboard do Parse
		//npm install -g parse-dashboard

		//Comando para abrir o dashboard do projeto
		//parse-dashboard --appId UWQqkFBBLixO34hKJKWa13ZMygVOOYJd7SJIZvGW --masterKey eRcPhFbRSMuHOjAOPEwaOBwx5wMTbn0tCVj7a4Qg --serverURL "https://parseapi.back4app.com/" --appName Instagram

		Parse.enableLocalDatastore(this);

		//Codigo configuração do app
		Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
				.applicationId("UWQqkFBBLixO34hKJKWa13ZMygVOOYJd7SJIZvGW")
				.clientKey("8IXI4gUJYXvZEN4I8yZfEP1kfbZbYGXVkc1hVF0n")
				.server("https://parseapi.back4app.com/")
				.build()
		);

		/*
		ParseObject pontuacao = new ParseObject("Pontuacao");
		pontuacao.put("pontos",100);
		pontuacao.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e==null){
					Log.d("debugParse","Sucesso");
				}else{
					Log.d("debugParse","ERROUUUU");
				}
			}
		});*/

//		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();

		//O default do acess level é masterKey, ou seja, conseguiremos atualizar e ler dados passando o MasterKey para o Parse
		//ParseACL.setDefaultACL(defaultACL, true);

		//Seta a atualização e leitura como publica (não sera necessario enviar o masterKey
		defaultACL.setPublicReadAccess(true);
		defaultACL.setPublicWriteAccess(true);
	}
}
