package com.pradheep.roomdb.viewmodel.view_model_provider

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pradheep.roomdb.network.api_instance.ApiService

//class ViewModelProviderFactory(private val com.helloalfred.repository.ApiRepository: com.helloalfred.repository.ApiRepository,) : ViewModelProvider.Factory {
//
//    @RequiresApi(Build.VERSION_CODES.R)
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return MoviesViewModel(com.helloalfred.repository.ApiRepository) as T
//    }
//}

class ViewModelProviderFactory<T : ViewModel>(
    private val viewModelClass: Class<T>,
    private val apiRepository: ApiService,
    private val additionalParameter1: String? = null,
    private val activity: Activity? = null,
    private val additionalParameter2: Int? = null,
    private val additionalParameter3: String? = null // Additional optional String parameter
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModelClass)) {
            try {
                // Try with all parameters including Activity
                val constructor = viewModelClass.getConstructor(ApiService::class.java, String::class.java, Activity::class.java, Int::class.javaPrimitiveType, String::class.java)
                return constructor.newInstance(apiRepository, additionalParameter1, activity, additionalParameter2, additionalParameter3) as T
            } catch (e: NoSuchMethodException) {
                try {
                    // Try without additionalParameter3
                    val constructor = viewModelClass.getConstructor(ApiService::class.java, String::class.java, Activity::class.java, Int::class.javaPrimitiveType)
                    return constructor.newInstance(apiRepository, additionalParameter1, activity, additionalParameter2) as T
                } catch (e: NoSuchMethodException) {
                    try {
                        // Try without additionalParameter2
                        val constructor = viewModelClass.getConstructor(ApiService::class.java, String::class.java, Activity::class.java)
                        return constructor.newInstance(apiRepository, additionalParameter1, activity) as T
                    } catch (e: NoSuchMethodException) {
                        try {
                            // Try without Activity
                            val constructor = viewModelClass.getConstructor(ApiService::class.java, String::class.java)
                            return constructor.newInstance(apiRepository, additionalParameter1) as T
                        } catch (e: NoSuchMethodException) {
                            // Try without additionalParameter1
                            val constructor = viewModelClass.getConstructor(ApiService::class.java)
                            return constructor.newInstance(apiRepository) as T
                        }
                    }
                }
            } catch (e: Exception) {
                throw IllegalArgumentException("Cannot instantiate ViewModel. Unknown error occurred: ${e.message}")
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


