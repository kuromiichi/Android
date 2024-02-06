package dev.kuromiichi.examenrecumoviles.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.kuromiichi.examenrecumoviles.R
import dev.kuromiichi.examenrecumoviles.databinding.ItemCiudadBinding
import dev.kuromiichi.examenrecumoviles.models.Ciudad
import dev.kuromiichi.examenrecumoviles.models.Registro

class CiudadAdapter(
    private var ciudades: List<Ciudad>,
    val listener: CiudadOnClickListener
) : RecyclerView.Adapter<CiudadAdapter.ViewHolder>() {
    inner class ViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCiudadBinding.bind(view)

        fun bind(ciudad: Ciudad) {
            binding.tvCiudad.text = ciudad.nombre
            binding.tvVotosEmitidos.text = ciudad.votosEmitidos.toString()
            binding.tvVotosBlancos.text = ciudad.votosBlancos.toString()
            binding.tvVotosNulos.text = ciudad.votosNulos.toString()
        }

        fun setListeners(ciudad: Ciudad) {
            binding.tvCiudad.setOnClickListener {
                listener.onClick(ciudad)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_ciudad,
            parent,
            false
        )
        return ViewHolder(view as ViewGroup)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ciudad = ciudades[position]
        holder.bind(ciudad)
    }

    override fun getItemCount(): Int = ciudades.size
}