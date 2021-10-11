package com.example.a04_creaciondevistasporcodigo;

import android.content.Intent;
import android.os.Bundle;

import com.example.a04_creaciondevistasporcodigo.activities.AddAlumnoActivity;
import com.example.a04_creaciondevistasporcodigo.activities.EditActivity;
import com.example.a04_creaciondevistasporcodigo.modelos.Alumno;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.a04_creaciondevistasporcodigo.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent>launcherAdd;
    private ActivityResultLauncher<Intent>launcherEdit;

    /**
     * -Conjunto de datos para los alumnos -> ArrayList
     * -Contenedor donde mostrar todos los alumnos -> Scroll -> LinearLayout(ScrollView)
     * -Plantilla con formato de cada alumno a mostrar ->xml (Especifico para el Alumno)
     */

    private ArrayList<Alumno> alumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        alumnos = new ArrayList<>();
        launcherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    Alumno alumno = result.getData().getExtras().getParcelable("ALUMNO");
                    alumnos.add(alumno);
                    mostrarAlumnos();
                }
            }
        });

        launcherEdit = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== RESULT_OK){
                    int posicion = result.getData().getExtras().getInt("POSICION");
                    Alumno alumno = result.getData().getExtras().getParcelable("ALUMNO");
                    if(alumno == null){
                        alumnos.remove(posicion);
                    }else{
                        alumnos.set(posicion,alumno);
                    }
                    mostrarAlumnos();
                }
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcherAdd.launch(new Intent(MainActivity.this, AddAlumnoActivity.class));
            }
        });

        mostrarAlumnos();
    }

    private void mostrarAlumnos() {

        binding.contentView.contenedor.removeAllViews();
        int contador =0;
        for (Alumno a: alumnos) {

            //Paso 1. Instanciar la vista (card)
            View card = LayoutInflater.from(this).inflate(R.layout.alumno_card,null);

            TextView lbNombre = card.findViewById(R.id.lbNombreAlmunoCard);
            TextView lbApellidos = card.findViewById(R.id.lbApellidosAlumnoCard);
            TextView lbCiclos = card.findViewById(R.id.lbCiclosAlumnoCard);
            TextView lbGrupo = card.findViewById(R.id.lbGrupoAlumnoCard);

            int finalContador = contador;
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("ALUMNO", a);
                    bundle.putInt("POSICION", finalContador);
                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                    intent.putExtras(bundle);
                    launcherEdit.launch(intent);

                }
            });

            contador++;

            //Paso 2. Rellenar los datos

            lbNombre.setText(a.getNombre());
            lbApellidos.setText(a.getApellidos());
            lbCiclos.setText(a.getCiclo());
            lbGrupo.setText(String.valueOf(a.getGrupo()));

            //Paso 3. Agregar el card al contenedor

            binding.contentView.contenedor.addView(card);

        }
    }


}

