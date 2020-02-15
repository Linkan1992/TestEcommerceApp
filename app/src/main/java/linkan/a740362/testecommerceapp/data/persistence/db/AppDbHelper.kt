package linkan.a740362.testecommerceapp.data.persistence.db

import linkan.a740362.testecommerceapp.di.annotation.CoroutineScopeIO
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppDbHelper @Inject
constructor(@CoroutineScopeIO private val ioCoroutineScope: CoroutineScope) :
    DbHelper {

    // override method declared in DbHelper interface

}
