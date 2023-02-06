package tech.solutionarchitects.testapplication.settings

import kotlinx.coroutines.flow.MutableStateFlow

object SettingsData{

    var rtb: MutableStateFlow<Boolean> = MutableStateFlow(false)
}
