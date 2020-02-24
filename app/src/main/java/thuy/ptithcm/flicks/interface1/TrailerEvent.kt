package thuy.ptithcm.flicks.interface1

import thuy.ptithcm.flicks.model.Movie
import thuy.ptithcm.flicks.model.Youtube

interface TrailerEvent {
    fun onItemTrailerClick(item: Youtube?)
}