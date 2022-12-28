package com.example.projetdma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projetdma.R;
import com.example.projetdma.Weather;
import com.koushikdutta.ion.Ion;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class DailyWeatherAdapter extends ArrayAdapter<Weather> {

    private Context context;
    private List<Weather>  weatherList;

    public DailyWeatherAdapter(@NonNull Context context,@NonNull List<Weather> weatherList) {
        super(context, 0, weatherList);
        this.context=context;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // in va convertir notre item_weather.xml a View (f la page main.xml) par la fonction inflate
        convertView = LayoutInflater.from(context).inflate(R.layout.item_weather, parent, false);

        TextView tvDate = convertView.findViewById(R.id.tvDate);
        TextView tvTemp = convertView.findViewById(R.id.tvTemp);
        ImageView iconWeather = convertView.findViewById(R.id.iconWeather);

        Weather weather = weatherList.get(position);

        // 20 C
        tvTemp.setText(weather.getTemp()+"°C");

        // iconWeather
        Ion.with(context)
                .load("https://openweathermap.org/img/w/"+weather.getIcon()+".png")
                .intoImageView(iconWeather);

        // la date , lzm tkon b format day,month num
        Date date = new Date(weather.getDate()*1000); // 7AWALNAHA L micro seconde
        DateFormat dateFormat = new SimpleDateFormat("EEE , MMM yy", Locale.ENGLISH);
        tvDate.setText(dateFormat.format(date));

        return convertView;
    }


}