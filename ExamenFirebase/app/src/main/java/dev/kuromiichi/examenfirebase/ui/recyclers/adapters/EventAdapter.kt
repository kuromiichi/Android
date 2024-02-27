package dev.kuromiichi.examenfirebase.ui.recyclers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.kuromiichi.examenfirebase.R
import dev.kuromiichi.examenfirebase.databinding.ItemEventBinding
import dev.kuromiichi.examenfirebase.models.Event
import dev.kuromiichi.examenfirebase.ui.recyclers.listeners.EventOnClickListener

class EventAdapter(
    private var items: List<Event>,
    private val listener: EventOnClickListener
) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemEventBinding.bind(view)
        fun bind(item: Event) {
            Glide.with(binding.ivImagen).load(item.imgUrl).into(binding.ivImagen)
            binding.tvFamilia.text = item.familia
            binding.tvTitulo.text = item.titulo
            binding.tvPonente.text = item.ponentes
            binding.tvHora.text = item.hora
            binding.tvLugar.text = item.lugar
            binding.tvTipo.text = item.tipo
        }

        fun setListener(item: Event) {
            binding.root.setOnClickListener { listener.onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_event,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.setListener(items[position])
    }

    fun update(items: List<Event>) {
        this.items = items
        notifyDataSetChanged()
    }
}