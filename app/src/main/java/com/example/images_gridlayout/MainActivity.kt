package com.example.images_gridlayout

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.images_gridlayout.TabSync.TabbedListMediator
import com.example.images_gridlayout.adapters.ItemsAdapter
import com.example.images_gridlayout.adapters.LoaderAdapter
import com.example.images_gridlayout.databinding.ActivityMainBinding
import com.example.images_gridlayout.databinding.ItemItemBinding
import com.example.images_gridlayout.modelView.MainActivityViewModel
import com.example.images_gridlayout.modelView.MainActivityViewModelFactory
import com.example.images_gridlayout.models.CatsCategory
import com.example.images_gridlayout.repository.CatRepository
import com.example.images_gridlayout.utils.InjectorUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: MainActivityViewModelFactory
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: ItemsAdapter
    private lateinit var tabbedListMediator: TabbedListMediator
    private lateinit var itemBinding: ItemItemBinding
    private var recyclerViewPool = RecyclerView.RecycledViewPool()
    private lateinit var indicesList: List<Int>
    private var categoryList: List<CatsCategory> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initUi()
        initRecyclerView()
        indicesList = categoryList.indices.toList()

    }

    fun initUi() {
        factory = InjectorUtils.provideMainActivityViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)
            .get(MainActivityViewModel::class.java)

        viewModel.getStatus().observe(this, Observer {
            when (it) {
                CatRepository.CatApiStatus.ERROR -> showError(true)
                else -> showError(false)
            }
        })
        viewModel.getCategories().observe(this, Observer { item ->
            categoryList = item
            indicesList = categoryList.indices.toList()
            initTabLayout()
            initMediator()
            fetchCat()
//            binding.recyclerview.adapter?.notifyDataSetChanged()
        })
    }

    fun showError(boolean: Boolean) {
        binding.error.isVisible = boolean
    }

    fun initTabLayout() {
//        viewModel.categoryMap.value
        if (categoryList.isEmpty()) {
            binding.tabs.addTab((binding.tabs.newTab().setText("")))
        } else {
            for (category in categoryList) {
                binding.tabs.addTab(binding.tabs.newTab().setText(category.name))
            }
        }
    }

    private fun fetchCat() {
        lifecycleScope.launch {
            viewModel.fetchImage(categoryList).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initRecyclerView() {
        adapter = ItemsAdapter()
        val footerAdapter = LoaderAdapter(adapter::retry)
        binding.outerrecyclerview.adapter = adapter.withLoadStateFooter(
            footer = footerAdapter
        )
        binding.outerrecyclerview.layoutManager = GridLayoutManager(this, 4)
        (binding.outerrecyclerview.layoutManager as GridLayoutManager).setSpanSizeLookup(object :
            GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == adapter.itemCount && footerAdapter.itemCount > 0)
                    return 4
                if (adapter.getItemViewType(position) == 1) return 4
                else
                    return 1
            }
        })
        val pool = RecyclerView.RecycledViewPool()
        pool.setMaxRecycledViews(1, 100000)
        pool.setMaxRecycledViews(0, 100000)
        binding.outerrecyclerview.setRecycledViewPool(pool)
        binding.outerrecyclerview.setHasFixedSize(true)

    }

    private fun initMediator() {
        tabbedListMediator = TabbedListMediator(
            binding.outerrecyclerview,
            binding.tabs,
            indicesList, true
        )
        tabbedListMediator.attach()

    }
}