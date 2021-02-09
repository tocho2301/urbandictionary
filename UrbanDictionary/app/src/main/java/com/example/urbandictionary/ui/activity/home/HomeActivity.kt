package com.example.urbandictionary.ui.activity.home

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urbandictionary.R
import com.example.urbandictionary.databinding.ActivityHomeBinding
import com.example.urbandictionary.di.UrbanDictionaryApplication
import com.example.urbandictionary.ui.activity.splash.SplashViewModel
import com.example.urbandictionary.ui.adapter.WordDefinitionAdapter
import javax.inject.Inject


class HomeActivity : AppCompatActivity() {

    lateinit var binding : ActivityHomeBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }

    @Inject lateinit var wordDefinitionAdapter : WordDefinitionAdapter

    @Inject lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as UrbanDictionaryApplication).getComponent().inject(this)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.getLocalDefinitions(binding.etSearch.text.toString())
                viewModel.getDefinitions(binding.etSearch.text.toString())
            }
            false
        }

        binding.clResult.ivSortBy.setOnClickListener {
            viewModel.onOrderByButtonClicked()
        }

        setUpRecyclerView()
        setUpObservables()
    }

    private fun setUpRecyclerView() {
        binding.clResult.rvDefinitions.layoutManager = linearLayoutManager
        binding.clResult.rvDefinitions.adapter = wordDefinitionAdapter
    }

    private fun setUpObservables() {
        viewModel.showDefinitions.observe(this, { wordDefinitionList ->
            (binding.clResult.rvDefinitions.adapter as WordDefinitionAdapter).updateData(wordDefinitionList)
            binding.clResult.tvWord.text = wordDefinitionList[0].word
        })

        viewModel.showError.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.showProgressBar.observe(this, { makeVisible ->
            if (makeVisible) {
                binding.pbLoadDefinitions.visibility = ProgressBar.VISIBLE
            } else {
                binding.pbLoadDefinitions.visibility = ProgressBar.GONE
            }
        })

        viewModel.showResultLayout.observe(this, { makeVisible ->
            if (makeVisible) {
                binding.clResult.root.visibility = LinearLayout.VISIBLE
            } else {
                binding.clResult.root.visibility = LinearLayout.GONE
            }
        })

        viewModel.openOrderOptionsDialog.observe(this, {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.order_by_dialog_title)
                .setItems(R.array.dialog_options) { _, which ->
                    when (which) {
                        0 -> viewModel.onOrderByThumbsUp()
                        1 -> viewModel.onOrderByThumbsDown()
                    }
                }
            builder.create().show()
        })

        viewModel.orderListBy.observe(this, { value ->
            when (value) {
                0 -> (binding.clResult.rvDefinitions.adapter as WordDefinitionAdapter).orderByThumbsUp()
                1 -> (binding.clResult.rvDefinitions.adapter as WordDefinitionAdapter).orderByThumbsDown()
            }
        })

        viewModel.changeOrderByText.observe(this, { text ->
            binding.clResult.tvORderByTitle.text = text
        })


    }


}