package com.zig.gps.view.Documents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zig.gps.databinding.DocumentsBinding
import com.zig.gps.databinding.TrackingBinding
import com.zig.gps.databinding.UserDashboardBinding
import com.zig.gps.view.DashboardNew
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DocmentsFragment : Fragment() {

    private var _binding: DocumentsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DocumentsBinding.inflate(inflater, container, false)
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun create(): DocmentsFragment {
            return DocmentsFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}