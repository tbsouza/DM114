package siecola.com.br.dm114;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import siecola.com.br.dm114.R;
import siecola.com.br.dm114.fragments.GCMFragment;
import siecola.com.br.dm114.fragments.LoginFragment;
import siecola.com.br.dm114.fragments.OrdersFragment;
import siecola.com.br.dm114.fragments.ProductsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Nome do shared preference de login e senha do usuario
    private static String PREF_LOGIN = "pref_login";
    private static String PREF_PASSWORD = "pref_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // mostra o primeiro fragmento ao iniciar a aplicação
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Mostra o fragment selecionado
        showFragment(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(int id) {

        Class fragmentClass;
        Fragment fragment = null; // instancia do fragmento a ser exibido
        int backStackEntryCount = getFragmentManager().getBackStackEntryCount();

        // Limpa o back stack
        for (int j = 0; j < backStackEntryCount; j++) {
            getFragmentManager().popBackStack();
        }

        try {
            switch (id) {
                case R.id.nav_login:
                    fragmentClass = LoginFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;
                case R.id.nav_produtos:
                    fragmentClass = ProductsFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;
                case R.id.nav_pedidos:
                    fragmentClass = OrdersFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;
                case R.id.nav_notificacoes:
                    fragmentClass = GCMFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;
                default:
                    fragmentClass = LoginFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();
        }
    }

    // Cria um canal de notificacao
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", "ThiagoVendas", importance);
            channel.setDescription("Orders");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
