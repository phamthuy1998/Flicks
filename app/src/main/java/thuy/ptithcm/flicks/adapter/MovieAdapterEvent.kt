package thuy.ptithcm.flicks.adapter

import thuy.ptithcm.flicks.model.Movie

interface MovieAdapterEvent {
    fun onItemMovieClick(item: Movie?)
    fun onLoadMore()
}