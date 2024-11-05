package com.m4.notes.ui.fragments.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.m4.notes.R
import com.m4.notes.databinding.FragmentOnBoardBinding
import com.m4.notes.ui.adapters.OnBoardAdapter


class OnBoardFragment : Fragment() {

    lateinit var binding: FragmentOnBoardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListeners()
    }

    private fun initialize() {
        binding.viewPager.adapter = OnBoardAdapter(this)
        TabLayoutMediator(binding.intoTabLayout, binding.viewPager) { _, _ -> }.attach()
    }

    private fun setupListeners() = with(binding.viewPager){
        binding.btnStart.alpha = 0f;
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
                binding.apply {
                    if (position == 2) {
                        tvSkip.animate().alpha(0f).setDuration(shortAnimationDuration.toLong()).setListener(null)
                        btnStart.animate().translationY(0f).alpha(1f);
                        btnStart.setOnClickListener {
                            findNavController().navigate(R.id.action_onBoardFragment_to_noteFragment)
                        }
                    } else {
                        btnStart.animate().translationY(600f).alpha(0f)
                        tvSkip.animate().alpha(1f).setDuration(shortAnimationDuration.toLong()).setListener(null)
                        tvSkip.setOnClickListener {
                            if (currentItem < 3){
                                setCurrentItem( currentItem + 2, true)
                            }
                        }
                    }
                }

            }
        })



    }
}