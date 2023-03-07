/*
 * Copyright 2022 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.mywallet.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.example.mywallet.databinding.ActivityCheckoutBinding
import com.google.android.gms.pay.Pay
import com.google.android.gms.pay.PayApiAvailabilityStatus
import com.google.android.gms.pay.PayClient


/**
 * Checkout implementation for the app
 */
class CheckoutActivity : AppCompatActivity() {

    private lateinit var layout: ActivityCheckoutBinding
    private lateinit var addToGoogleWalletButton: View

    // TODO: Save the JWT from the backend "response"
    private val token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ3YWxsZXQtYW5kcm9pZC1jbGllbnRAZmlyc3Qtb3V0bGV0LTM3ODMwNy5pYW0uZ3NlcnZpY2VhY2NvdW50LmNvbSIsImF1ZCI6Imdvb2dsZSIsIm9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDozMDAwIl0sInR5cCI6InNhdmV0b3dhbGxldCIsInBheWxvYWQiOnsiZ2VuZXJpY09iamVjdHMiOlt7ImlkIjoiMzM4ODAwMDAwMDAyMjE5Nzc0MS5jb2RlbGFiX29iamVjdCIsImNsYXNzSWQiOiIzMzg4MDAwMDAwMDIyMTk3NzQxLm15LXdhbGxldC01NTk5IiwiZ2VuZXJpY1R5cGUiOiJHRU5FUklDX1RZUEVfVU5TUEVDSUZJRUQiLCJoZXhCYWNrZ3JvdW5kQ29sb3IiOiIjNDI4NWY0IiwibG9nbyI6eyJzb3VyY2VVcmkiOnsidXJpIjoiaHR0cHM6Ly9zdG9yYWdlLmdvb2dsZWFwaXMuY29tL3dhbGxldC1sYWItdG9vbHMtY29kZWxhYi1hcnRpZmFjdHMtcHVibGljL3Bhc3NfZ29vZ2xlX2xvZ28uanBnIn19LCJjYXJkVGl0bGUiOnsiZGVmYXVsdFZhbHVlIjp7Imxhbmd1YWdlIjoiZW4tVVMiLCJ2YWx1ZSI6Ikdvb2dsZSBJL08gJzIyIn19LCJzdWJoZWFkZXIiOnsiZGVmYXVsdFZhbHVlIjp7Imxhbmd1YWdlIjoiZW4tVVMiLCJ2YWx1ZSI6IkF0dGVuZGVlIn19LCJoZWFkZXIiOnsiZGVmYXVsdFZhbHVlIjp7Imxhbmd1YWdlIjoiZW4tVVMiLCJ2YWx1ZSI6IkFsZXggTWNKYWNvYnMifX0sImJhcmNvZGUiOnsidHlwZSI6IlFSX0NPREUiLCJ2YWx1ZSI6IjMzODgwMDAwMDAwMjIxOTc3NDEuY29kZWxhYl9vYmplY3QifSwiaGVyb0ltYWdlIjp7InNvdXJjZVVyaSI6eyJ1cmkiOiJodHRwczovL3N0b3JhZ2UuZ29vZ2xlYXBpcy5jb20vd2FsbGV0LWxhYi10b29scy1jb2RlbGFiLWFydGlmYWN0cy1wdWJsaWMvZ29vZ2xlLWlvLWhlcm8tZGVtby1vbmx5LmpwZyJ9fSwidGV4dE1vZHVsZXNEYXRhIjpbeyJoZWFkZXIiOiJQT0lOVFMiLCJib2R5IjoiMTIzNCIsImlkIjoicG9pbnRzIn0seyJoZWFkZXIiOiJDT05UQUNUUyIsImJvZHkiOiIyMCIsImlkIjoiY29udGFjdHMifV19XX0sImlhdCI6MTY3ODExNzU3N30.iaVu0BoorN2d0lXw5pigpkw8YJdXOcsmIHoa94jF58lBBnQCBFqhjSzzOBm8o2HWStybawlDIugNv7UuSb2n8MUKq-MtPm0g5wWd7uyyk7wc62m9_FHnDNd6-HY8TvX1NRPK0LmAK65-yk9fOeFwV56jTGvtIzmXzbbuRns5rebdpEndWT0U9Aod3jbiv6lT45syEx-KSdA7wLjNPAYKKn_7I_SRextUjOS9BE43FlifmpJeVYY4Frz0UaQIdCFdrjMt5mtQzMIp2ppAmFYzxzHjuSAO-IQxRnQxDN0ngbar9ZbYeLKMD7DmgoQAx4GlY6TX2aj1FWtLHx8BUubb5w"

    // TODO: Add a request code for the save operation
    private val addToGoogleWalletRequestCode = 1000

    // TODO: Create a client to interact with the Google Wallet API
    private lateinit var walletClient: PayClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Instantiate the Pay client
        walletClient = Pay.getClient(this)

        // Use view binding to access the UI elements
        layout = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(layout.root)

        // TODO: Check if the Google Wallet API is available
        fetchCanUseGoogleWalletApi()

        // TODO: Set an on-click listener on the "Add to Google Wallet" button
        addToGoogleWalletButton = layout.addToGoogleWalletButton.root
        addToGoogleWalletButton.setOnClickListener {
            walletClient.savePasses(
                token,
                this,
                addToGoogleWalletRequestCode
            )
        }
    }

    // TODO: Create a method to check for the Google Wallet SDK and API
    private fun fetchCanUseGoogleWalletApi() {
        walletClient
            .getPayApiAvailabilityStatus(PayClient.RequestType.SAVE_PASSES)
            .addOnSuccessListener { status ->
                if (status == PayApiAvailabilityStatus.AVAILABLE)
                    layout.passContainer.visibility = View.VISIBLE
            }
            .addOnFailureListener {
                // Hide the button and optionally show an error message
            }
    }

    // TODO: Handle the result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == addToGoogleWalletRequestCode) {
            when (resultCode) {
                RESULT_OK -> {
                    // Pass saved successfully. Consider informing the user.
                    Log.d("onActivityResult", "Pass saved successfully. Consider informing the user.")
                }
                RESULT_CANCELED -> {
                    // Save canceled
                    Log.d("onActivityResult", "Save canceled.")
                }

                PayClient.SavePassesResult.SAVE_ERROR -> data?.let { intentData ->
                    val errorMessage = intentData.getStringExtra(PayClient.EXTRA_API_ERROR_MESSAGE)
                    // Handle error. Consider informing the user.
                    Log.e("onActivityResult", "SavePassesResult $errorMessage")
                }

                else -> {
                    // Handle unexpected (non-API) exception
                    Log.w("onActivityResult", "Handle unexpected (non-API) exception")
                }
            }
        }
    }
}
