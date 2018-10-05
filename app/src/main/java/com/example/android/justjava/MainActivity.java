/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */

package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static com.example.android.justjava.R.id.notify_me_checkbox;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
       if(quantity<0)
           quantity=0;
           //displayPrice(quantity * 5);
        EditText simpleEditText = (EditText) findViewById(R.id.simpleEditText);
        String editTextValue = simpleEditText.getText().toString();
        CheckBox whip=(CheckBox)findViewById(notify_me_checkbox);
        boolean b=whip.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        //Log.v("MainActivity", "has whip cream" + b);
        int p=calculateprice(b,hasChocolate);
       String pmsg=orderSummary(p,b,hasChocolate,editTextValue);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.order_summary_email_subject,editTextValue));
        intent.putExtra(Intent.EXTRA_TEXT, pmsg);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
    public void increment(View view){
        /*quantity=quantity+1;
        /*if(quantity<0)
            quantity=1;
        display(quantity);
        if(quantity>100)
        {
            Context context = getApplicationContext();
            CharSequence text = "No more than 100";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            quantity=100;
            */
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        display(quantity);

    }
    public void decrement(View view){


        /*quantity=quantity-1;
        /*if(quantity>=0)
        display(quantity);
        if(quantity<1)
        {
            Context context = getApplicationContext();
            CharSequence text = "No less than 1";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            quantity=1;
        }
        else
            display(quantity);
            */
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
        

    }
    /**
     * This method displays the given price on the screen.
     */
    /*private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }*/
   /*private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }*/
    private  String orderSummary(int price, boolean whip,boolean hasc,String name)
    {
        String priceMessage=getString(R.string.order_summary_name,name);
        priceMessage+= "\n"+ getString(R.string.order_summary_whipped_cream,whip);
        priceMessage+="\n"+getString(R.string.order_summary_chocolate,hasc);

         priceMessage+= "\n"+getString(R.string.order_summary_quantity,quantity);

        priceMessage=priceMessage+"\n"+getString(R.string.order_summary_price,NumberFormat.getCurrencyInstance().format(price));
        priceMessage=priceMessage+"\n" + getString(R.string.thank_you);
        return  priceMessage;
    }
    private  int calculateprice(boolean w,boolean t){
        int basePrice=5;
        if(w)
            basePrice=basePrice+1;
         if(t)
            basePrice=basePrice+2;
        return quantity*basePrice;
    }



}