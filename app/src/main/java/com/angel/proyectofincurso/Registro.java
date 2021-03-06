package com.angel.proyectofincurso;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class Registro extends AppCompatActivity {

    private static final int SELECT_IMAGE = 100;
    TextView registro;
    EditText usuario;
    EditText contrasena;
    EditText contrasena2;
    EditText email;
    Button btnseleccionimagen;
    ImageView ivAvatar;
    String imagepath;
    Button btninicioregistro;
    FirebaseAuth aut;
    ProgressDialog progressDialog;
    FirebaseStorage storage;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        registro = findViewById(R.id.textregistroinicio);
        usuario = findViewById(R.id.edtcontrasenaregistro);
        email = findViewById(R.id.edtemailregistro);
        contrasena = findViewById(R.id.edtcontrasenaregistro);
        btninicioregistro = findViewById(R.id.btninicioregistro);
        aut= FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        storage = FirebaseStorage.getInstance();



        /*btnseleccionimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);


                startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), SELECT_IMAGE);
            }
        });*/

        btninicioregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertaUsuario(usuario, contrasena,contrasena2, email, imagepath);


            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    public void insertaUsuario(EditText nombreUsuario, EditText contrasena,EditText contrasena2, EditText email, String ivAvatar) {

        String emailr = email.getText().toString().trim();
        String contrasenar = contrasena.getText().toString().trim();
        if (TextUtils.isEmpty(emailr)) {
            Toast.makeText(this, "Escriba el email", Toast.LENGTH_SHORT).show();
            return;
        }else if(contrasenar.length()<6){
            Toast.makeText(this, "La contraseña debe tener 6 caracteres como mínimo", Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(contrasenar)) {
            Toast.makeText(this, "Escriba la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }else {
            progressDialog.setMessage("Registrando usuario...");
            progressDialog.show();
            aut.createUserWithEmailAndPassword(emailr, contrasenar).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                        progressDialog.dismiss();
                        finish();
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        Toast.makeText(getApplicationContext(), "Verifica tu correo", Toast.LENGTH_SHORT).show();


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Fallo en el registro,vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        /*Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final RealmResults<Usuario> usuarios = realm.where(Usuario.class).equalTo("usuario", String.valueOf(nombreUsuario.getText())).findAll();
        final RealmResults<Usuario> usuarios2 = realm.where(Usuario.class).findAll();
        if (usuarios.size() == 0) {
            if(!contrasena.getText().toString().equals(contrasena2.getText().toString())){
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            }else{
            Number puppies = realm.where(Usuario.class).max("id");
            long longId;
            final RealmResults<Usuario> puppies2 = realm.where(Usuario.class).findAll();
            Usuario usuario = new Usuario();
            usuario.setUsuario(nombreUsuario.getText().toString());
            usuario.setEmail(String.valueOf(email.getText()));
            usuario.setContrasena(new String(Hex.encodeHex(DigestUtils.md5(String.valueOf(contrasena.getText())))));
            usuario.setAvatar(ivAvatar);
                if(puppies==null){
                    longId= 1;
                }else{
                    longId=puppies.longValue()+1;
                }

            usuario.setId(longId);
            realm.copyToRealm(usuario);
            realm.commitTransaction();
            Toast.makeText(Registro.this, "Uusario creado", Toast.LENGTH_LONG).show();
            }
        } else {

            Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_LONG).show();
            usuarios.deleteFromRealm(0);
            realm.commitTransaction();
        }*/

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    StorageReference storageRef = storage.getReference();
                    Uri selectedImage = data.getData();

                    Uri file =selectedImage;
                    StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
                    UploadTask uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                        }
                    });



                    /*InputStream stream = null;
                    try {
                        stream = new FileInputStream(new File(selectedImage.toString()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    ImageView imageView = new ImageView(Registro.this);
                    imageView.setImageURI(selectedImage);
                    imageView.setDrawingCacheEnabled(true);
                    imageView.buildDrawingCache();
                    File file = new File(selectedImage.toString());
                    String imageName = file.getName();
                    StorageReference mountainsRef = storageRef.child(imageName);
                    StorageReference mountainImagesRef = storageRef.child("images/"+imageName);

                    UploadTask uploadTask = mountainsRef.putStream(stream);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                        }
                    });*/

                    /*ImageView imageView = new ImageView(Registro.this);
                    imageView.setImageURI(selectedImage);
                    imageView.setDrawingCacheEnabled(true);
                    imageView.buildDrawingCache();
                    File file = new File(selectedImage.toString());
                    String imageName = file.getName();
                    StorageReference mountainsRef = storageRef.child(imageName);
                    StorageReference mountainImagesRef = storageRef.child("images/"+imageName);

                    imageView.setDrawingCacheEnabled(true);
                    imageView.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data2 = baos.toByteArray();

                    UploadTask uploadTask = mountainImagesRef.putBytes(data2);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(Registro.this, "", Toast.LENGTH_LONG).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                            Toast.makeText(Registro.this, "", Toast.LENGTH_LONG).show();
                        }
                    });*/
                    /*imagepath = selectedImage.toString();
                    ivAvatar.setImageURI(selectedImage);*/
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

