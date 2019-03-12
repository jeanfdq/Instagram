package com.instagram.model;

import android.content.Context;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class UserSharePhoto {

	private Context context;
	private String username;
	private String userfullname;
	private String postdata;
	private ParseFile photo;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserfullname() {
		return userfullname;
	}

	public void setUserfullname(String userfullname) {
		this.userfullname = userfullname;
	}

	public String getPostdata() {
		return postdata;
	}

	public void setPostdata(String postdata) {
		this.postdata = postdata;
	}

	public ParseFile getPhoto() {
		return photo;
	}

	public void setPhoto(ParseFile photo) {
		this.photo = photo;
	}

	public void PostPhoto(Context c){

		this.context = c;

		ParseObject object = new ParseObject("postagem");
		object.put("username",this.username);
		object.put("userfullname",this.userfullname);
		object.put("postdata",this.postdata);
		object.put("photo",this.photo);

		object.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {

				if (e == null){
					Toast.makeText(context, "Foto postada com sucesso!",Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(context, "Erro: "+e.getMessage().trim(),Toast.LENGTH_LONG).show();
				}
			}
		});
	}

}
