package com.example.todolistapp.ui.theme

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// 6th thing implemented
@HiltAndroidApp
// TodoApp must also be defined in the androidManifest as android:name=".ui.theme.TodoApp"
class TodoApp: Application()