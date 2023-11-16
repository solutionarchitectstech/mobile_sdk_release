/*
 * Copyright © Gusev Andrew, Emelianov Andrew, Spinov Dmitry [collectively referred as the Authors], 2017 - All Rights Reserved
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

package tech.solutionarchitects.testapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.testapplication.activity.CreativeActivity
import tech.solutionarchitects.testapplication.activity.MultipleProductCreativesActivity
import tech.solutionarchitects.testapplication.activity.ProductCreativeActivity
import tech.solutionarchitects.testapplication.databinding.ActivityMainBinding
import tech.solutionarchitects.testapplication.utils.requestLocation

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestLocation()

        // Creative

        binding.creativeButton.setOnClickListener {
            startActivity(Intent(this, CreativeActivity::class.java))
        }

        // Product creative
        binding.productCreativeButton.setOnClickListener {
            startActivity(Intent(this, ProductCreativeActivity::class.java))
        }

        // MULTIPLE IMPRESSION (EXPERIMENTAL)

        binding.openMultipleProductCreative.setOnClickListener {
            startActivity(Intent(this, MultipleProductCreativesActivity::class.java))
        }
    }

}
