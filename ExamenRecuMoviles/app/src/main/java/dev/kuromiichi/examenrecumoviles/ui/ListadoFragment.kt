package dev.kuromiichi.examenrecumoviles.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.kuromiichi.examenrecumoviles.R
import dev.kuromiichi.examenrecumoviles.databinding.FragmentListadoBinding

class ListadoFragment : Fragment() {
    private var _binding: FragmentListadoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListadoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtons()
    }

    private fun setButtons() {
        binding.btnNoFiltro.setOnClickListener {
            binding.etFiltro.apply {
                visibility = View.GONE
                text?.clear()
            }
        }

        binding.btnFiltro.setOnClickListener {
            binding.etFiltro.visibility = View.VISIBLE
        }
    }
}