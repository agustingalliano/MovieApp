package com.example.movie_app.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movie_app.R
import com.example.movie_app.databinding.ActivityMainBinding
import com.example.movie_app.listeners.FragmentCallBackListener
import com.example.movie_app.ui.view.fragments.DetailFragment
import com.example.movie_app.ui.view.fragments.ListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val listFragment = ListFragment()
    private val detailsFragment = DetailFragment()
    private var firstReleasedFragment : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, listFragment)
            if(firstReleasedFragment) {
                firstReleasedFragment = false
            } else {
                addToBackStack(null)
            }
            commit()
        }

        listFragment.setFragmentCallBack1(object: FragmentCallBackListener {
            override fun onCallBack(id: Int) {
                supportFragmentManager.beginTransaction().apply {
                    val bundle = Bundle()
                    bundle.putInt("id", id)
                    detailsFragment.arguments = bundle
                    replace(R.id.container, detailsFragment)
                    addToBackStack(null)
                    commit()
                }
            }
        })
    }
}