package com.zig.gps.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.zig.gps.R
import com.zig.gps.databinding.ActivityMainBinding
import com.zig.gps.utils.DataHandler
import com.zig.gps.utils.SpUtil
import com.zig.gps.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController : NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var sp: SpUtil

    val viewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(binding.container.navHostFragmentContentMain.id) as NavHostFragment? ?: return
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration.Builder(R.id.tabLayoutDemo).build()
        setupActionBarWithNavController(navController, appBarConfiguration)


        onBackPressedDispatcher.addCallback(this /* lifecycle owner */) {
            if (!navController.popBackStack()) {
                finishAfterTransition()
            }
        }


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment , R.id.splashScreen-> {
                    window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                    binding.toolbar.visibility = View.GONE // to hide toolbar
                }

                R.id.tabLayoutDemo -> {
                    window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                    binding.toolbar.visibility = View.VISIBLE // to hide toolbar
                }
                R.id.dashboardNew2 -> {
                    window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                    binding.toolbar.visibility = View.VISIBLE // to hide toolbar
                }


            }
        }


        lifecycleScope.launchWhenStarted {
            viewModel.userLogoutResponse.collect { dataHandler ->
                when (dataHandler) {
                    is DataHandler.SUCCESS -> {
                        Log.d("test002","SUCCESS ")
                        sp.save("user_session", false)
                        //finish()

                    navController.navigateUp()

                    }
                    is DataHandler.ERROR -> {
                        Log.d("test002","failure "+ dataHandler.message)

                        sp.save("user_session", false)
                        navController.navigateUp()
                       // binding.progressBar.hide()
                     //   LogData("onViewCreated: ERROR " + dataHandler.message)
                    }
                    is DataHandler.EMPTY -> {
                        Log.d("test002","loading")
                       // LogData("onViewCreated: LOADING..")
                    }
                }
            }

        }



        }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_logout ->{

                Log.d("test002","logout ")
                viewModel.userLogout("Bearer ${sp.getValueString("user_access_token")}")

                //navController.navigateUp()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}