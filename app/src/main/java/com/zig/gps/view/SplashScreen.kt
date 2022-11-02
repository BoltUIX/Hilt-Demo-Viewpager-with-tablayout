package com.zig.gps.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zig.gps.R
import com.zig.gps.databinding.UserDashboardBinding
import com.zig.gps.databinding.UserSplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashScreen : Fragment() {

    private var _binding: UserSplashScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private var handler: Handler = Handler(Looper.myLooper()!!)
    var runnable: Runnable? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = UserSplashScreenBinding.inflate(inflater, container, false)
        return binding.root


    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigateHome(1500)


    }


    private fun navigateHome(delay: Long) {


        handler.postDelayed(Runnable {
            try{

                if (findNavController().currentDestination!!.id == R.id.splashScreen) {
                    findNavController().navigate(R.id.action_splashScreen_to_loginFragment)
                }


            }catch (e: Exception){
            }
        }.also { runnable = it }, delay)



    }

    override fun onStop() {
        super.onStop()
        try{
            if(runnable!=null){
                handler.removeCallbacks(runnable!!)
            }
            else{

            }
        }
        catch (e:Exception){

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}