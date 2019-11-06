package by.viktor.ranoappcover.utils

import by.viktor.ranoappcover.models.Movies

interface IFirebaseLoadDone {

    fun onFirebaseLoadSuccess(movieList:List<Movies>)
    fun onFirebaseLoadFailed(message: String)
}