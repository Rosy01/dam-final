package com.example.app_calendario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

public class CalendPersonalizado extends AppCompatActivity implements CalendarView.OnDateChangeListener {

    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calend_personalizado);

        calendarView=(CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);



    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int i, int i1, int i2) {
        //preparar dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence []items = new CharSequence[3];
        items[0]="Agregar Evento";
        items[1]="Ver Eventos";
        items[2]="Cancelar";

        int  dia, mes, anio;
        dia = i;
        mes = i1+1;
        anio = i2;

        builder.setTitle("Selecciona una Tarea")
                .setItems(items, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            //agregar eventos
                            Intent intent = new Intent(getApplication(), AddActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("dia", dia);
                            bundle.putInt("mes", mes);
                            bundle.putInt("anio", anio);
                            intent.putExtras(bundle);
                            startActivity(intent);


                        }else if (i==1){
                            //ver eventos
                            Intent intent = new Intent(getApplication(), VerActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("dia", dia);
                            bundle.putInt("mes", mes);
                            bundle.putInt("anio", anio);
                            intent.putExtras(bundle);
                            startActivity(intent);


                        }else{
                            return;
                        }

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

        }
    }
