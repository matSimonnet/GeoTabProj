package sm.tb.geotabproj;

import java.io.File;

import org.mapsforge.android.maps.MapActivity;
import org.mapsforge.android.maps.MapController;
import org.mapsforge.core.GeoPoint;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

@SuppressLint("SdCardPath")
public class Geotab_activity extends MapActivity {
	
	//View Scale
	static float viewScale = (float)2.0;
	//Tile Scale
	static int mapScale = 18;
	
	//private MapView mapView;
	private GeoTabMapView geoTabMapView;
	private MapController mapController;
	private TextToSpeech tts = null;
	
	private String folder = "map";
	private String map = "porsman";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    		requestWindowFeature(Window.FEATURE_NO_TITLE);
    		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
            
        MapSDWriter.write( getResources().openRawResource(R.raw.porsman), folder, map);
    	
        OnInitListener onInitListener = new OnInitListener() {
    		@Override
    		public void onInit(int status) {
    		}
    	};
    	tts = new TextToSpeech(this, onInitListener);
        
        
        //creates mapview
        geoTabMapView = new GeoTabMapView(this);
        //geoTabMapView.setMapFile(new File(Environment.getExternalStorageDirectory().getPath()+ "/" + folder + "/" + map + ".map"));
        geoTabMapView.setMapFile(new File(Environment.getExternalStorageDirectory().getPath()+ "/map/midi-pyrenees.map"));
        //geoTabMapView.setMapFile(new File(Environment.getExternalStorageDirectory().getPath()+ "/map/bretagne.map"));
        
        mapController = geoTabMapView.getController();
		
        //Porsman
        //mapController.setCenter(new GeoPoint(48.4426, -4.778));
        //Toulouse
        mapController.setCenter(new GeoPoint(43.6037, 1.441779));
		
        mapController.setZoom(mapScale);
       
		geoTabMapView.setScaleX(viewScale);
		geoTabMapView.setScaleY(viewScale);

        //Fill view;
        setContentView(geoTabMapView);
		
    }

	public TextToSpeech getTts() {
		return tts;
	}

	public void setTts(TextToSpeech tts) {
		this.tts = tts;
	}

	@Override
	protected void onDestroy() {
		Log.i("GeoTabActivity","onDestroy()");
		if (geoTabMapView.tts != null){
			geoTabMapView.tts.stop();
			geoTabMapView.tts.shutdown();
		}
		super.onDestroy();	
	}

	@Override
	protected void onPause() {
		Log.i("GeoTabActivity","onPause()");
		super.onPause();

	}

	@Override
	protected void onResume() {
		Log.i("GeoTabActivity","onResume()");
		super.onResume();
	}

}
