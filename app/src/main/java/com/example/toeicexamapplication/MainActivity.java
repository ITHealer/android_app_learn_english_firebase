package com.example.toeicexamapplication;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.toeicexamapplication.account.MyProfileActivity;
import com.example.toeicexamapplication.account.RankingActivity;
import com.example.toeicexamapplication.account.SignInActivity;
import com.example.toeicexamapplication.databinding.ActivityMainBinding;
import com.example.toeicexamapplication.listening.ListeningActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private NavigationView navigationView;
    private MenuItem mLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWidgets();

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_learn_vocabulary, R.id.nav_grammar, R.id.nav_listening,
                R.id.nav_reading, R.id.nav_my_profile, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        handlerMenuItem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        ImageView btnSearch = (ImageView) findViewById(R.id.img_search);
        EditText searchItem = findViewById(R.id.edt_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cho vui (lười)
                String t = searchItem.getText().toString().trim();
                if (t.equals("Nghe")) {
                    startActivity(new Intent(getApplication(), ListeningActivity.class));
                }
                if (t.equals("Xep hang")) {
                    startActivity(new Intent(getApplication(), RankingActivity.class));
                }
                if (t.equals("Tai khoa")) {
                    startActivity(new Intent(getApplication(), MyProfileActivity.class));
                }
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    //bắt sự kiện trên menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_intro:
                dialogIntro();
                break;
            case R.id.action_huong_dan:
                Toast.makeText(this, "Nhóm sẽ phát triển sau", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "Nhóm sẽ phát triển sau", Toast.LENGTH_LONG).show();
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogIntro(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_intro_app);

        Button btnHuy = (Button) dialog.findViewById(R.id.btn_huy);

        //setCanceledOnTouchOutside khi chạm bên ngoài dialog sẽ k bị tắt; mặc định là true
        dialog.setCanceledOnTouchOutside(true);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getWidgets() {
        navigationView = binding.navView;
        mLogOut = binding.navView.getMenu().findItem(R.id.nav_logout);
    }

    //Xử lý đăng xuất
    private void handlerMenuItem() {
        mLogOut.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
                return false;
            }
        });
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}