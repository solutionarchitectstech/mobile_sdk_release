/*
 * Copyright © Gusev Andrew, Emelianov Andrew, Spinov Dmitry [collectively referred as the Authors], 2017-2022 - All Rights Reserved
 * [NOTICE: All information contained herein is, and remains the property of the Authors.](notice: All information contained herein is, and remains the property of the Authors.)
 * The intellectual and technical concepts contained herein are proprietary to the Authors
 * and may be covered by any existing patents of any country in the world, patents in
 * process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material is strictly forbidden unless prior
 * written permission is obtained from the Authors. Access to the source code contained herein is hereby forbidden
 * to anyone except persons (natural person, corporate or unincorporated body, whether or not having a separate
 * legal personality, and that person’s personal representatives, and successors)
 * the Authors have granted permission and who have executed Confidentiality and Non-disclosure agreements
 * explicitly covering such access.
 *
 * The copyright notice above does not provide evidence of any actual or intended publication or disclosure
 * of this source code, which in
 */

package tech.solutionarchitects.testapplication.activity

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.TechAdvertising
import tech.solutionarchitects.testapplication.databinding.ActivitySettingsBinding
import tech.solutionarchitects.testapplication.settings.SettingsStorage

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val url = binding.baseUrlTextEdit.text.toString()
            SettingsStorage.baseUrl = url
            TechAdvertising.setBaseUrl(url, this)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.baseUrlTextEdit.setText(SettingsStorage.baseUrl)
        binding.baseUrlTextEdit.postDelayed({
            binding.baseUrlTextEdit.requestFocus()
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(
                binding.baseUrlTextEdit,
                InputMethodManager.SHOW_IMPLICIT
            )
            binding.baseUrlTextEdit.setSelection(binding.baseUrlTextEdit.text?.length ?: 0)

        }, 500)
    }
}