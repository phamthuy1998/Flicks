package thuy.ptithcm.flicks.adapter

import thuy.ptithcm.flicks.model.Youtube

interface TrailerAdapterEvent {
    fun onItemMovieClick(item: Youtube?)
}