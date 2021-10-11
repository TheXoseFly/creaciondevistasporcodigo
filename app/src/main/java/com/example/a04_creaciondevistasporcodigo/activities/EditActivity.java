package com.example.a04_creaciondevistasporcodigo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.a04_creaciondevistasporcodigo.R;
import com.example.a04_creaciondevistasporcodigo.databinding.ActivityEditBinding;
import com.example.a04_creaciondevistasporcodigo.modelos.Alumno;

public class EditActivity extends AppCompatActivity {

    private ActivityEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inicializaValores();

        binding.btnBorrarAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("POSICION", getIntent().getExtras().getInt("POSICION"));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        binding.btnGuardarEditAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = binding.txtNombreEditAlumno.getText().toString();
                String apellidos = binding.txtApellidosEditAlumno.getText().toString();
                int indiceSpinner = binding.spCiclosEditAlumno.getSelectedItemPosition();
                int idRaddioButton = binding.rgGrupoEditAlumno.getCheckedRadioButtonId();

                if(!nombre.isEmpty() && !apellidos.isEmpty() && indiceSpinner!=0 && idRaddioButton!=-1){
                    String ciclo = binding.spCiclosEditAlumno.getSelectedItem().toString();
                    char grupo = ((RadioButton)findViewById(idRaddioButton)).getText().toString().charAt(0);  //Porque el idraddiobutton nos devuelve el que está marcado
                    Alumno alumno = new Alumno(nombre, apellidos,ciclo, grupo);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("ALUMNO",alumno);
                    bundle.putInt("POSICION", getIntent().getExtras().getInt("POSICION"));
                    Intent intent  = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();


                }else{
                    Toast.makeText(EditActivity.this, "Te faltan datos parcela", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }

    private void inicializaValores() {
        Alumno alumno = getIntent().getExtras().getParcelable("ALUMNO");
        binding.txtNombreEditAlumno.setText(alumno.getNombre());
        binding.txtApellidosEditAlumno.setText(alumno.getApellidos());

        switch (alumno.getCiclo()) {
            case "DAM":
                binding.spCiclosEditAlumno.setSelection(1);
                break;
            case "DAW":
                binding.spCiclosEditAlumno.setSelection(2);
                break;
            case "SMR":
                binding.spCiclosEditAlumno.setSelection(3);
                break;
            case "3D":
                binding.spCiclosEditAlumno.setSelection(4);
                break;

        }

        switch (alumno.getGrupo()) {
            case 'A':
                binding.rbAEditAlumno.setChecked(true);
                break;
            case 'B':
                binding.rbAEditAlumno.setChecked(true);
                break;
            case 'C':
                binding.rbAEditAlumno.setChecked(true);
                break;
        }
    }
}