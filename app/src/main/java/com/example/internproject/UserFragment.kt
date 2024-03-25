package com.example.internproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.example.internproject.databinding.FragmentUserBinding
import kotlinx.coroutines.launch

class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)

        val accessToken = arguments?.getString("twitchAccessToken")

//        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val repository = Repository()

        if (accessToken != null) {
//            userViewModel.getUsers(accessToken, TwitchConstants.clientId, "141981764")

//            userViewModel.result.observe(viewLifecycleOwner) {users ->
//                for(user in users) {
//                    binding.textUser.append("Id : ${user.id}")
//                    binding.textUser.append("Display Name : ${user.displayName}")
//                    binding.textUser.append("Broadcaster Type : ${user.broadcasterType}")
//                }
//            }
            repository.getUsers(accessToken, TwitchConstants.clientId, "654851614", binding.textUser)
        }
// zua979mhbwmay1etpjbomx86cyaqy7
        // Inflate the layout for this fragment
        return binding.root
    }

}