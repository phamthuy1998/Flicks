package thuy.ptithcm.flicks.interface1

import thuy.ptithcm.flicks.model.Movie

interface MovieAdapterEvent {

    fun onItemMovieClick(item: Movie?)

    fun onLoadMore()
}