package com.instagram.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.instagram.R;
import com.instagram.fragments.FragmentHome;
import com.instagram.fragments.FragmentProfile;
import com.instagram.fragments.FragmentShared;

public class MainActivity extends AppCompatActivity {

	private Toolbar toolbar;
	private ImageView camera, direct;
	private BottomNavigationView bottomNavigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Toolbar---------------------------------------------------------------------------------------------------
		setToolbar();
		//----------------------------------------------------------------------------------------------------------


		camera = findViewById(R.id.main_toolbar_camera);
		camera.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		direct = findViewById(R.id.main_toolbar_direct);
		direct.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});


		bottomNavigationView = findViewById(R.id.main_bottomnavigation);

		//Deixar o menu selected
		Menu menu = bottomNavigationView.getMenu();
		MenuItem menuItem = menu.getItem(0);
		menuItem.setChecked(true);

		//Iniciar com o fragment Home------
		loadFragment(new FragmentHome());
		//---------------------------------

		bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem menuItem) {

				Fragment fragment = null;

				switch (menuItem.getItemId()) {

					case R.id.navigation_main_home:
						fragment = new FragmentHome();
						hiddenToolbar(false);
						setToolbar();
						break;

					case R.id.navigation_main_add_photos:
						fragment = new FragmentShared();
						hiddenToolbar(false);
						break;

					case R.id.navigation_main_perfil:
						fragment = new FragmentProfile();
						hiddenToolbar(true);
						break;
				}

				return loadFragment(fragment);
			}
		});

	}

	private void setToolbar() {
		toolbar = findViewById(R.id.main_toolbar);
		toolbar.setContentInsetsAbsolute(0, 0); //Deixar o width da toolbar match_parent
		toolbar.setTitle("");
		setSupportActionBar(toolbar);
		getSupportActionBar().show();
	}

	private boolean loadFragment(Fragment fragment) {
		if (fragment != null) {

			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.main_frame, fragment)
					.commit();
			return true;
		}
		return false;
	}

	private void hiddenToolbar(boolean IsHidden) {
		if (IsHidden)
			getSupportActionBar().hide();
		else
			getSupportActionBar().show();
	}


}
