package dev.kuromiichi.examenfirebase.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.kuromiichi.examenfirebase.R
import dev.kuromiichi.examenfirebase.databinding.FragmentHomeBinding
import dev.kuromiichi.examenfirebase.models.Event
import dev.kuromiichi.examenfirebase.ui.recyclers.adapters.EventAdapter
import dev.kuromiichi.examenfirebase.ui.recyclers.listeners.EventOnClickListener
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class HomeFragment : Fragment(), EventOnClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val db by lazy { Firebase.firestore }

    private var events: List<Event> = emptyList()
    private val adapter by lazy { EventAdapter(events, this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtons()
        setRecycler()
    }

    private fun setButtons() {
        binding.btnEventos.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_eventsFragment)
        }
    }

    private fun setRecycler() {
        binding.rvEventos.apply {
            adapter = this@HomeFragment.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        updateEvents()
    }

    private fun updateEvents() {
        runBlocking {
            events = db.collection("events").get().await().toObjects(Event::class.java)
        }
        adapter.update(events)
    }

    override fun onClick(event: Event) {
        TODO("Not yet implemented")
    }
}