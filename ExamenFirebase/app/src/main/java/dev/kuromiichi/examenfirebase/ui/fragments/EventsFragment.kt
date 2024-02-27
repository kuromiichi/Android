package dev.kuromiichi.examenfirebase.ui.fragments

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dev.kuromiichi.examenfirebase.databinding.DialogCreateEventBinding
import dev.kuromiichi.examenfirebase.databinding.FragmentEventsBinding
import dev.kuromiichi.examenfirebase.models.Event
import dev.kuromiichi.examenfirebase.ui.recyclers.adapters.EventAdapter
import dev.kuromiichi.examenfirebase.ui.recyclers.listeners.EventOnClickListener
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class EventsFragment : Fragment(), EventOnClickListener {
    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!

    private val auth by lazy { Firebase.auth }
    private val db by lazy { Firebase.firestore }
    private val storage by lazy { Firebase.storage }

    private var events: List<Event> = emptyList()
    private val adapter by lazy { EventAdapter(events, this) }
    private var imageUri: Uri? = null
    private var selectedImage: ActivityResultLauncher<PickVisualMediaRequest>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        selectedImage =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    imageUri = uri
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error al seleccionar la imagen",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecycler()
        setButton()
    }

    private fun setRecycler() {
        binding.rvEventos.apply {
            adapter = this@EventsFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
        updateEvents()
    }

    private fun updateEvents() {
        runBlocking {
            events = db.collection("events").get().await().toObjects(Event::class.java)
        }
        adapter.update(events)
    }

    private fun setButton() {
        binding.btnCrear.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                val binding = DialogCreateEventBinding.inflate(layoutInflater)
                setView(binding.root)

                binding.ivImagen.setOnClickListener {
                    selectedImage?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    binding.ivImagen.setImageURI(imageUri)
                }

                setTitle("Crear evento")

                setPositiveButton("Crear") { _, _ ->
                    if (anyEmpty(
                            binding.tilFamilia.editText?.text.toString(),
                            binding.tilGrupo.editText?.text.toString(),
                            binding.tilTipo.editText?.text.toString(),
                            binding.tilTitulo.editText?.text.toString(),
                            binding.tilOrganismo.editText?.text.toString(),
                            binding.tilPonente.editText?.text.toString(),
                            binding.tilHora.editText?.text.toString(),
                            binding.tilLugar.editText?.text.toString(),
                            imageUri.toString()
                        )
                    ) {
                        Toast.makeText(
                            requireContext(),
                            "Todos los campos son obligatorios",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setPositiveButton
                    }

                    val event = Event(
                        "",
                        binding.tilFamilia.editText?.text.toString(),
                        binding.tilGrupo.editText?.text.toString(),
                        binding.tilTipo.editText?.text.toString(),
                        binding.tilTitulo.editText?.text.toString(),
                        imageUri.toString(),
                        binding.tilOrganismo.editText?.text.toString(),
                        binding.tilPonente.editText?.text.toString(),
                        binding.tilHora.editText?.text.toString(),
                        binding.tilLugar.editText?.text.toString()
                    )

                    db.collection("events").add(event).addOnSuccessListener { result ->
                        db.collection("events").document(result.id).update("id", result.id)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    requireContext(), "Evento creado con éxito", Toast.LENGTH_SHORT
                                ).show()
                            }
                    }

                    uploadImage()
                }

                setNegativeButton("Cancelar") { _, _ -> }
            }.show()

            updateEvents()
        }
    }

    private fun anyEmpty(vararg strings: String): Boolean {
        return strings.any { it.isBlank() }
    }

    private fun uploadImage() {
        val uid = auth.currentUser!!.uid
        val storageReference = storage.reference.child("images").child(uid)

        if (imageUri != null) {
            storageReference.putFile(imageUri!!).addOnSuccessListener {
                Toast.makeText(
                    requireContext(), "Imagen subida correctamente", Toast.LENGTH_SHORT
                ).show()
            }.addOnFailureListener {
                Toast.makeText(
                    requireContext(), "Error al subir la imagen: ${it.message}", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onClick(event: Event) {
        TODO("Not yet implemented")
    }
}