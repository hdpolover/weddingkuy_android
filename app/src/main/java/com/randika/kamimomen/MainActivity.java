package com.randika.kamimomen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.randika.kamimomen.api.ApiClient;
import com.randika.kamimomen.api.ApiInterface;
import com.randika.kamimomen.fragments.LokasiFragmentAdapter;

public class MainActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    TabLayout tabV;
    ViewPager2 viewPageTab;

    LokasiFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbarMy);

        setSupportActionBar(toolbar);

        apiInterface = ApiClient.getClient();
        tabV = findViewById(R.id.tabV);
        viewPageTab = findViewById(R.id.viewPageTab);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new LokasiFragmentAdapter(fm, getLifecycle());
        viewPageTab.setAdapter(adapter);

        tabV.addTab(tabV.newTab().setText("WO"));
        tabV.addTab(tabV.newTab().setText("Fotografi"));
        tabV.addTab(tabV.newTab().setText("Souvenir"));

        tabV.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPageTab.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPageTab.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabV.selectTab(tabV.getTabAt(position));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.loginAdminMenu:
                startActivity(new Intent(MainActivity.this, LoginAdminActivity.class));
                finish();
                return true;

            case R.id.scanMenu:
                startActivity(new Intent(MainActivity.this, LoginPengantinActivity.class));
                finish();
                return true;

            case R.id.aboutMenu:
                startActivity(new Intent(MainActivity.this, TentangAplikasiActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}