package linkan.a740362.testecommerceapp.di.component

import android.app.Application
import linkan.a740362.testecommerceapp.di.builderClass.ActivityBuilder
import linkan.a740362.testecommerceapp.di.module.AppModule
import linkan.a740362.testecommerceapp.di.module.NetworkModule
import linkan.a740362.testecommerceapp.di.module.PersistenceModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        AppModule::class,
        PersistenceModule::class,
        NetworkModule::class,
        ActivityBuilder::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent

    }

    override fun inject(instance: DaggerApplication)
}
