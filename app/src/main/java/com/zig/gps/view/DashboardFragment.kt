package com.zig.gps.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zig.gps.R
import com.zig.gps.databinding.UserDashboardBinding
import com.zig.gps.utils.SpUtil
import com.zig.gps.utils.pie.PieChart
import com.zig.gps.utils.pie.Slice
import com.zig.gps.utils.pie.buildChart
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random


@AndroidEntryPoint
class DashboardFragment : Fragment() {


    @Inject
    lateinit var sp: SpUtil

    private var _binding: UserDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = UserDashboardBinding.inflate(inflater, container, false)
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("test001","${sp.getValueString("user_name")}")

        binding.apply {
            // Kotlin DSL example
            val pieChartDSL = buildChart {
                slices { provideSlices() }
                sliceWidth { 80f }
                sliceStartPoint { 0f }
                clickListener { angle, index ->
                    // ...
                }
            }
            chart.setPieChart(pieChartDSL)
            chart.showLegend(legendLayout)

            val pieChart = PieChart(
                slices = provideSlices(), clickListener = null, sliceStartPoint = 0f, sliceWidth = 80f
            ).build()

            chart.setPieChart(pieChart)
            chart.showLegend(legendLayout)


        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun provideSlices(): ArrayList<Slice> {
        return arrayListOf(
            Slice(
                Random.nextInt(1000, 3000).toFloat(),
                R.color.materialRed700,
                "Running"
            ),
            Slice(
                Random.nextInt(1000, 2000).toFloat(),
                R.color.brown700,
                "Stopped"
            ),
            Slice(
                Random.nextInt(1000, 5000).toFloat(),
                R.color.colorPrimary,
                "Awaiting Data"
            ),
            Slice(
                Random.nextInt(1000, 10000).toFloat(),
                R.color.grey_20,
                "Device Offline"
            ),
            Slice(
                Random.nextInt(1000, 10000).toFloat(),
                R.color.colorAccent,
                "Expired"
            ),
            Slice(
                Random.nextInt(1000, 10000).toFloat(),
                R.color.materialIndigo600,
                "No Device"
            )


        )
    }
}