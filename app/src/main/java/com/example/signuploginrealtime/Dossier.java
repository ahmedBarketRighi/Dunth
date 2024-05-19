package com.example.signuploginrealtime;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class Dossier extends AppCompatActivity {
    private EditText editText;
    private LinearLayout wordsLayout;
    int cond=0;
    String cc;
    private final int GALLERY_REQ_CODE1=100;
    private final int GALLERY_REQ_CODE2=101;
    private final int GALLERY_REQ_CODE3=102;
    private String gender;
    FirebaseDatabase database;
    DatabaseReference reference;
    ImageView chronic_imgv,allgerie_imgv,insurance_imgv;
    String medications;
    Button btnregisterdossier;
    String[] item={"Diabetes"," Hypertension ","Asthma"," Chronic Obstructive Pulmonary Disease","Alzheimer's Disease"};
    ArrayAdapter<String> adapteritems;
    String[] item2={"Pollen"," Dust mites","Pet dander","Mold","Insect stings","Food allergies"};
    ArrayAdapter<String> adapteritems2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier);
         editText = findViewById(R.id.editText);

        wordsLayout = findViewById(R.id.wordsLayout);

        Intent intent = getIntent();

        String username = intent.getStringExtra("username");

        //Toast.makeText(this, "username "+username, Toast.LENGTH_SHORT).show();




        TextInputLayout textInputLayout = findViewById(R.id.fullnamelayout);
        TextInputEditText fullname = textInputLayout.findViewById(R.id.fullname);



        TextInputLayout textInputLayout1 = findViewById(R.id.fullfathernamelayout);
        TextInputEditText fullfathername = textInputLayout1.findViewById(R.id.fullfathername);
        TextInputLayout textInputLayout2 = findViewById(R.id.fullmothernamelayout);
        TextInputEditText fullmothername = textInputLayout2.findViewById(R.id.fullmothername);
        TextInputLayout textInputLayout3 = findViewById(R.id.adreess_layout);
        TextInputEditText fulladdress = textInputLayout3.findViewById(R.id.adressuser);
        TextInputLayout textInputLayout4 = findViewById(R.id.dateofbirthlayout);
        TextInputEditText dateofbirth = textInputLayout4.findViewById(R.id.dateofbirth);
        TextInputLayout textInputLayout5 = findViewById(R.id.chronic_illnesses_layout);
        AutoCompleteTextView chronicillnesses = textInputLayout5.findViewById(R.id.autoCompleteTextView_chronic);


        TextInputLayout textInputLayout6 = findViewById(R.id.allergieslayout);
        AutoCompleteTextView allergies = textInputLayout6.findViewById(R.id.autocompletetv_allergie);
        TextInputLayout textInputLayout9 = findViewById(R.id.grouplaout);
        TextInputEditText group = textInputLayout9.findViewById(R.id.groupdossier);

        RadioGroup radioGroupgrnder = findViewById(R.id.radioGroupgender);

       RadioButton radioaccept=findViewById(R.id.radioButtonaccept);
        btnregisterdossier=findViewById(R.id.register_your_dossier);
        TextInputLayout textInputLayout7 = findViewById(R.id.Heightlayout);
        TextInputEditText height = textInputLayout7.findViewById(R.id.Height);

        TextInputLayout textInputLayout8 = findViewById(R.id.Weightlayout);
        TextInputEditText weight = textInputLayout8.findViewById(R.id.Weight);

         chronic_imgv=findViewById(R.id.chronic_ill_image);
         allgerie_imgv=findViewById(R.id.allergie_image);
         insurance_imgv=findViewById(R.id.insurance_image);


     adapteritems=new ArrayAdapter<String>(this,R.layout.list_item_chronicillnesses,item);
        adapteritems2=new ArrayAdapter<String>(this,R.layout.list_item_chronicillnesses,item2);



        chronicillnesses.setAdapter(adapteritems);
        chronicillnesses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=parent.getItemAtPosition(position).toString();
            }
        });

        allergies.setAdapter(adapteritems2);
        allergies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=parent.getItemAtPosition(position).toString();
            }
        });






        chronic_imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dossier.this, "i will go to get the picture.", Toast.LENGTH_SHORT).show();
                 cond=1;
                Intent iGallery =new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY_REQ_CODE1);


            }
        });

        allgerie_imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cond=2;
                Intent iGallery =new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY_REQ_CODE2);

            }
        });

        insurance_imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cond=3;
                Intent iGallery =new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY_REQ_CODE3);

            }
        });









        radioGroupgrnder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if(radioButton != null) {
                    gender = radioButton.getText().toString();

                }
            }
        });


        radioaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               cc="accepte";

            }
        });



        btnregisterdossier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (!fullname.getText().toString().isEmpty() &&
                        !fullfathername.getText().toString().isEmpty() &&
                        !cc.isEmpty()&&
                        !fullmothername.getText().toString().isEmpty() &&
                        !fulladdress.getText().toString().isEmpty() &&
                        !dateofbirth.getText().toString().isEmpty() &&
                        !height.getText().toString().isEmpty() &&
                        !weight.getText().toString().isEmpty() &&
                        !gender.isEmpty()) {


            database = FirebaseDatabase.getInstance();
            reference = database.getReference("dossiers");
          String uri1=getImageUri(chronic_imgv);
            String uri2=getImageUri(allgerie_imgv);
            String uri3=getImageUri(insurance_imgv);

            medications=editText.getText().toString();
            String n=username;

            HelperDossier helperDossier=new HelperDossier(n,group.getText().toString(),fullname.getText().toString(),fullfathername.getText().toString(),fullmothername.getText().toString(),fulladdress.getText().toString(),
                    dateofbirth.getText().toString(),height.getText().toString(),weight.getText().toString(),gender,chronicillnesses.getText().toString(),allergies.getText().toString(),medications,uri1,uri2,uri3);
                  reference.child(username).setValue(helperDossier);
                    Toast.makeText(Dossier.this, "register success.", Toast.LENGTH_SHORT).show();
           finish();

        }
        else {

            Toast.makeText(Dossier.this, "Make sure that you have filled all required information.", Toast.LENGTH_SHORT).show();

        }



            }
        });



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // Toast.makeText(Dossier.this, "i am on activity result.", Toast.LENGTH_SHORT).show();

        if(resultCode== RESULT_OK && cond==1){
            chronic_imgv.setImageURI(data.getData());
        }
       if(resultCode== RESULT_OK && cond==2){
            allgerie_imgv.setImageURI(data.getData());
        }
        if(resultCode== RESULT_OK && cond==3){
            insurance_imgv.setImageURI(data.getData());
        }

    }


    private String getImageUri(ImageView imageex) {
        if (imageex.getDrawable() != null && imageex.getDrawable() instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) imageex.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageData = baos.toByteArray();

            // Generate a unique identifier for the image
            String uniqueId = UUID.randomUUID().toString();

            // Specify the base directory and generate the path for the image
            String basePath = "images/"; // You can change this to your desired directory
            String imagePath = basePath + uniqueId + ".jpg";

            // Get a reference to the Firebase Storage location where you want to store the image
            StorageReference imageRef = FirebaseStorage.getInstance().getReference().child(imagePath);

            // Upload image data to Firebase Storage
            UploadTask uploadTask = imageRef.putBytes(imageData);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                // Image uploaded successfully
                // You can handle any additional logic here
            }).addOnFailureListener(e -> {
                // Handle image upload failure
            });

            return imagePath; // Return the path to the uploaded image
        } else {
            return null; // No image selected
        }
    }






}