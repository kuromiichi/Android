package dev.kuromiichi.examenrecumoviles.ui

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import dev.kuromiichi.examenrecumoviles.database.VotosDatabase
import dev.kuromiichi.examenrecumoviles.databinding.FragmentVotoBinding
import dev.kuromiichi.examenrecumoviles.models.Registro

class VotoFragment : Fragment() {
    private var _binding: FragmentVotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpinner()
        setButtons()
    }

    private fun setSpinner() {
        binding.spinnerCiudad.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            listOf("Coruña", "Vigo", "Santiago", "Lugo")
        ).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
    }

    private fun setButtons() {
        binding.btnVoto.setOnClickListener {
            binding.tvVotosEmitidos.visibility = View.VISIBLE
            binding.tvVotosBlancos.visibility = View.VISIBLE
            binding.tvVotosNulos.visibility = View.VISIBLE
            binding.etVotosEmitidos.visibility = View.VISIBLE
            binding.etVotosBlancos.visibility = View.VISIBLE
            binding.etVotosNulos.visibility = View.VISIBLE
        }

        binding.btnAdd.setOnClickListener {
            if (validar()) {
                val ciudad = Registro(
                    0,
                    binding.spinnerCiudad.selectedItem.toString(),
                    binding.tilSeccion.editText?.text.toString(),
                    binding.tilMesa.editText?.text.toString().toInt(),
                    binding.etVotosEmitidos.text.toString().toInt(),
                    binding.etVotosBlancos.text.toString().toInt(),
                    binding.etVotosNulos.text.toString().toInt()
                )

                var success = true
                Thread {
                    try {
                        VotosDatabase.getInstance(requireContext()).ciudadDao().insert(ciudad)
                    } catch (e: SQLiteConstraintException) {
                        success = false
                    }
                }
                if (success) {
                    Toast.makeText(
                        requireContext(),
                        "Registro insertado correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.tilSeccion.editText?.text?.clear()
                    binding.tilMesa.editText?.text?.clear()
                    binding.tvVotosEmitidos.visibility = View.GONE
                    binding.tvVotosBlancos.visibility = View.GONE
                    binding.tvVotosNulos.visibility = View.GONE
                    binding.etVotosEmitidos.apply {
                        visibility = View.GONE
                        text?.clear()
                    }
                    binding.etVotosBlancos.apply {
                        visibility = View.GONE
                        text?.clear()
                    }
                    binding.etVotosNulos.apply {
                        visibility = View.GONE
                        text?.clear()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "No se ha podido insertar el registro",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Todos los campos son obligatorios",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun validar(): Boolean {
        return !(binding.tilSeccion.editText?.text.toString().isEmpty()
            || binding.tilMesa.editText?.text.toString().isEmpty()
            || binding.etVotosEmitidos.text.toString().isEmpty()
            || binding.etVotosBlancos.text.toString().isEmpty()
            || binding.etVotosNulos.text.toString().isEmpty())
    }
}