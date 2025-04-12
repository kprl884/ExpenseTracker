package com.uniquetech.expensetracker

import android.app.Application
import com.uniquetech.expensetracker.data.sample.SampleDataProvider
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ExpenseTrackerApp : Application() {
    
    @Inject
    lateinit var sampleDataProvider: SampleDataProvider
    
    override fun onCreate() {
        super.onCreate()
        
        // İlk çalıştırmada örnek verileri yüklüyoruz
        sampleDataProvider.populateSampleData()
    }
} 