package com.example.app_calendario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddCursos extends AppCompatActivity implements View.OnClickListener {

     EditText edtNombreEvento, edtDescripcionEvento, edtHora, edtFecha;

     Button guardar_evento,cancelar_evento;
     Button button_fecha,button_hora;
    private int dia,mes,anio,hora,minutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //referencia a atributos

        edtNombreEvento=(EditText) findViewById(R.id.edtNombreEvento);
        edtDescripcionEvento=(EditText) findViewById(R.id.edtDescripcionEvento);

        //obteber datos que se envia a esta actividad
        Bundle bundle = getIntent().getExtras();
        int dia=0, mes=0, anio=0;
        bundle.getInt("dia");
        bundle.getInt("mes");
        bundle.getInt("anio");
        edtFecha.setText(dia+" - "+mes+ " - "+anio);

        guardar_evento=(Button) findViewById(R.id.guardar_evento);
        cancelar_evento=(Button) findViewById(R.id.cancelar_evento);

        guardar_evento.setOnClickListener(this);
        cancelar_evento.setOnClickListener(this);

        button_fecha=(Button) findViewById(R.id.button_fecha);
        button_hora=(Button) findViewById(R.id.button_hora);
        edtHora=(EditText) findViewById(R.id.edtHora);
        edtFecha=(EditText) findViewById(R.id.edtFecha);
        button_fecha.setOnClickListener(this);
        button_hora.setOnClickListener(this);





    }


    @Override
    public void onClick(View v) {
        if (v==button_fecha){
            final Calendar c= Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            anio=c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    edtFecha.setText(dayOfMonth+"/"+(dayOfMonth+1)+"/"+year);
                }
            }
                    ,dia,mes,anio);
            datePickerDialog.show();

        }if(v==button_hora){
            final Calendar c= Calendar.getInstance();
            hora=c.get(Calendar.HOUR_OF_DAY);
            minutos=c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    edtHora.setText(hourOfDay+" : "+minute);
                }
            },hora,minutos,false);
            timePickerDialog.show();
        }
    }
}