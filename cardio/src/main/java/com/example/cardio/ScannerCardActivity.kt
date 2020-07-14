package com.example.cardio

import android.content.Intent
import android.os.Bundle
import com.example.cardiodinamictest.BaseSplitActivity
import io.card.payment.CardIOActivity
import io.card.payment.CreditCard


class ScannerCardActivity : BaseSplitActivity() {

    private val MY_SCAN_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner_card)

        setupCardIO()
    }

    private fun setupCardIO() {
        val scanIntent = Intent(this, CardIOActivity::class.java)

        // customize these values to suit your needs.

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true) // default: false

        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false) // default: false

        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false) // default: false


        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_SCAN_REQUEST_CODE) {
            var resultDisplayStr: String
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                val scanResult: CreditCard =
                    data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT)

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = """
                    Card Number: ${scanResult.redactedCardNumber}
                    
                    """.trimIndent()

                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );
                if (scanResult.isExpiryValid) {
                    resultDisplayStr += """
                        Expiration Date: ${scanResult.expiryMonth}/${scanResult.expiryYear}
                        
                        """.trimIndent()
                }
                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += """CVV has ${scanResult.cvv.length} digits.
"""
                }
                if (scanResult.postalCode != null) {
                    resultDisplayStr += """
                        Postal Code: ${scanResult.postalCode}
                        
                        """.trimIndent()
                }
            } else {
                resultDisplayStr = "Scan was canceled."
            }
            // do something with resultDisplayStr, maybe display it in a textView
            // resultTextView.setText(resultDisplayStr);
        }
        // else handle other activity results
    }
}
