package com.zig.gps.view

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.zig.gps.R
import com.zig.gps.databinding.UserSignInBinding
import com.zig.gps.utils.DataHandler
import com.zig.gps.utils.LogData
import com.zig.gps.utils.SpUtil
import com.zig.gps.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {

    val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var sp: SpUtil


    private var _binding: UserSignInBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = UserSignInBinding.inflate(inflater, container, false)
        return binding.root

    }


    private fun buildAuthHeader(username: String, password: String): String {
        val base = "$username:$password"
        return "Basic " + Base64.encodeToString(base.toByteArray(), Base64.NO_WRAP)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(sp.getValueBoolean("user_session",false)){
            findNavController().navigate(R.id.action_loginFragment_to_dashboardNew2)
        }

        binding.privacyId.movementMethod=LinkMovementMethod.getInstance() //to access 3rd party links

        binding.submitBtn.setOnClickListener {

        /*    sp.save("user_session",true)
            sp.save("user_name","hari")
            sp.save("user_id",12)
*/

         /*   Log.d("test001","${sp.getValueString("user_name")}")
            Log.d("test001","${sp.getValueBoolean("user_session",false)}")
            Log.d("test001","${sp.getValueInt("user_id")}")

*/

            if (binding.nameEditText.text.toString().trim()== "" ){

                Snackbar.make(binding.coordinator, R.string.SnackBar, Snackbar.LENGTH_LONG)
                    .setAction(R.string.SnackBarBtn) {
                        // Responds to click on the action
                    }
                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
                    .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.materialIndigo600))
                    .show()


            }
            else if (binding.passwordEditText.text.toString().trim()==""){

                Snackbar.make(binding.coordinator, R.string.SnackBarPass, Snackbar.LENGTH_LONG)
                    .setAction(R.string.SnackBarBtn) {
                        // Responds to click on the action
                    }
                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
                    .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.materialIndigo600))
                    .show()

            }
            else{

                //......................sign in
                var signInObj: JSONObject? = null
                try {
                    signInObj = JSONObject()
                    signInObj.put("phoneNumber", binding.nameEditText.text.toString().trim())
                    signInObj.put("password", binding.passwordEditText.text.toString().trim())
                    LogData("login request :$signInObj ")
                } catch (e : JSONException) {
                    e.printStackTrace()
                    //myLog("zig200", "login request :${e.message} ")
                }

                // Convert JSONObject to String
                val jsonObjectString = signInObj.toString()
                // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
                val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())


                binding.progressBar.show()
                viewModel.userLoginAuth(requestBody)
            }


        }



        binding.btnMobileNo.setOnClickListener{

            if (findNavController().currentDestination!!.id == R.id.loginFragment) {
                findNavController().navigate(R.id.action_loginFragment_to_loginOtpFragment)
            }


            //......................sign in


        }


        lifecycleScope.launchWhenStarted {
            viewModel.userLoginResponse.collect { dataHandler ->
                when (dataHandler) {
                    is DataHandler.SUCCESS -> {
                        binding.progressBar.hide()
                        LogData("onViewCreated: SUCCESS.. ${dataHandler.data!!.accessToken}")


                        sp.save("user_session",true)
                        sp.save("user_access_token", dataHandler.data.accessToken!!)
                        sp.save("user_refresh_token", dataHandler.data.refreshToken!!)


                        if (findNavController().currentDestination!!.id == R.id.loginFragment) {
                            findNavController().navigate(R.id.action_loginFragment_to_dashboardNew2)
                        }


                    }
                    is DataHandler.ERROR -> {
                        binding.progressBar.hide()
                        LogData("onViewCreated: ERROR " + dataHandler.message)
                    }
                    is DataHandler.EMPTY -> {
                        LogData("onViewCreated: LOADING..")
                    }
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init() {
    }
}