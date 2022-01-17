package com.example.fastmaths.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fastmaths.database.User
import com.example.fastmaths.database.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FastMathsViewModel: ViewModel() {

    var theUser: MutableLiveData<User> = MutableLiveData()


    init {
        theUser.postValue(User(-1,0,0))
    }

    fun getInformation(dao:UserDao): LiveData<User> {
        CoroutineScope(IO).launch {
            val data = async {
                dao.get()
            }.await()
            if (data.isNotEmpty()) {
                theUser.postValue(data[0])
            } else {
                Log.e("MAIN", "Unable to get data")
            }
        }
        return theUser
    }
    fun updateInformation(user:User,dao:UserDao){
        CoroutineScope(IO).launch {
            dao.update(user)
            theUser.postValue(user)
        }
    }

    fun addInformation(user:User,dao:UserDao){
        CoroutineScope(IO).launch {
            dao.add(user)
            theUser.postValue(dao.get()[0])
        }
    }
}