package com.meetrickandmorty.app.ui.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<D : BaseData>(protected val data: D) : ViewModel() {

    internal fun bindData(binding: ViewDataBinding, dataBR: Int) {
        binding.setVariable(dataBR, data)
    }
}