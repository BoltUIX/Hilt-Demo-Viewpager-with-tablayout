package com.zig.gps.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.LinearLayoutManager
import com.zig.gps.adapter.GetDeviceAdapter
import com.zig.gps.databinding.UserDashboardNewBinding
import com.zig.gps.utils.DataHandler
import com.zig.gps.utils.LogData
import com.zig.gps.view.Tracking.TrackingFragment
import com.zig.gps.viewModel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DashboardNew : Fragment() {

    private var _binding: UserDashboardNewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    @Inject
    lateinit var getDeviceAdaoter: GetDeviceAdapter

    val viewModel: DashboardViewModel by viewModels()






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = UserDashboardNewBinding.inflate(inflater, container, false)
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        init()


        try {
            lifecycleScope.launchWhenStarted {
                viewModel.getDeviceDetails.collect { dataHandler ->
                    when (dataHandler) {
                        is DataHandler.SUCCESS -> {
                            //binding.progressBar.visibility = View.GONE

                            LogData("onViewCreated: Success " + listOf(dataHandler.data).size)

                            //  getDeviceAdaoter.differ.submitList(listOf(dataHandler.data))
                            getDeviceAdaoter.differ.submitList(dataHandler.data)
                        }

                        is DataHandler.ERROR -> {
                            //binding.progressBar.visibility = View.GONE
                            LogData("onViewCreated: ERROR " + dataHandler.message)
                        }

                        is DataHandler.EMPTY -> {
                            //binding.progressBar.visibility = View.VISIBLE
                            LogData("onViewCreated: LOADING..")

                        }
                    }
                }
            }
            viewModel.getDeviceDetails()
        }catch (e:java.lang.Exception){}




    }

   /* companion object {
        fun create(): DashboardNew {
            return DashboardNew()
        }
    }*/
    companion object {
        fun newInstance() = DashboardNew()
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun init() {
        getDeviceAdaoter.onArticleClicked {
            /*val bundle = Bundle().apply {
                putParcelable("article_data", it)

            }
            findNavController().navigate(
                R.id.action_onlineFragment_to_articleDetailsFragment,
                bundle
            )*/
        }

        binding.recyclerView.apply {
            adapter = getDeviceAdaoter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}
