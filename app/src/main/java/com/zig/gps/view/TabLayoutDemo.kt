package com.zig.gps.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.zig.gps.R
import com.zig.gps.databinding.TabLayoutBinding
import com.zig.gps.view.Alerts.AlertsFragment
import com.zig.gps.view.Device.DeviceFragment
import com.zig.gps.view.Documents.DocmentsFragment
import com.zig.gps.view.Fastag.FastagFragment
import com.zig.gps.view.Maintanance.MaintananceFragment
import com.zig.gps.view.Report.ReportFragment
import com.zig.gps.view.Tracking.TrackingFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TabLayoutDemo : Fragment() {

    private var _binding: TabLayoutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = TabLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //renderViewPager
        binding.viewpager.adapter = object : FragmentStateAdapter(this) {

            override fun createFragment(position: Int): Fragment {
                return ResourceStore.pagerFragments[position]
            }

            override fun getItemCount(): Int {
                return ResourceStore.tabList.size
            }
        }


        //renderTabLayer
       var test =  TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = getString(ResourceStore.tabList[position])
        }.attach()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}

interface ResourceStore {
    companion object {
        val tabList = listOf(
            R.string.Dashboard, R.string.Tracking, R.string.Fastags,R.string.Reports, R.string.Documents, R.string.Maintance, R.string.Alerts, R.string.Device
        )
        val pagerFragments = listOf(
            DashboardNew.create(),
            TrackingFragment.create(),
            FastagFragment.create(),
            ReportFragment.create(),
            DocmentsFragment.create(),
            MaintananceFragment.create(),
            AlertsFragment.create(),
            DeviceFragment.create())
    }
}