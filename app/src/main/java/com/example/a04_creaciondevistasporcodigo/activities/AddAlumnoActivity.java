package com.example.a04_creaciondevistasporcodigo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.a04_creaciondevistasporcodigo.R;
import com.example.a04_creaciondevistasporcodigo.databinding.ActivityAddAlumnoBinding;
import com.example.a04_creaciondevistasporcodigo.modelos.Alumno;

public class AddAlumnoActivity extends AppCompatActivity {

    private ActivityAddAlumnoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAlumnoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCancelarAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        binding.btnGuardarAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = binding.txtNombreAddAlumno.getText().toString();
                String apellidos = binding.txtApellidosAddAlumno.getText().toString();
                int indiceSpinner = binding.spCiclosAddAlumno.getSelectedItemPosition();
                int idRaddioButton = binding.rgGrupoAddAlumno.getCheckedRadioButtonId();

                if(!nombre.isEmpty() && !apellidos.isEmpty() && indiceSpinner!=0 && idRaddioButton!=-1){
                    String ciclo = binding.spCiclosAddAlumno.getSelectedItem().toString();
                    char grupo = ((RadioButton)findViewById(idRaddioButton)).getText().toString().charAt(0);  //Porque el idraddiobutton nos devuelve el que está marcado
                    Alumno alumno = new Alumno(nombre, apellidos,ciclo, grupo);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("ALUMNO",alumno);
                    Intent intent  = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();


                }else{
                    Toast.makeText(AddAlumnoActivity.this, "Te faltan datos parcela", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}