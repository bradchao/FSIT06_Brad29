package tw.org.iii.appps.brad29;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView[] names = new TextView[4];
    private int[] rnames = {R.id.name1, R.id.name2, R.id.name3, R.id.name4};
    private MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i=0; i<names.length; i++) names[i] = findViewById(rnames[i]);

    }

    public void test1(View view) {
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("Brad","Tony","Kevin","Peter");
    }

    public void test2(View view) {
        if (myAsyncTask != null){
            Log.v("brad", "status:" + myAsyncTask.getStatus());
            if (!myAsyncTask.isCancelled()){
                myAsyncTask.cancel(true);
            }
        }
    }

    private class MyAsyncTask extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v("brad", "onPreExecute");
        }

        @Override
        protected void onPostExecute(String mesg) {
            super.onPostExecute(mesg);
            Log.v("brad", "onPostExecute:" + mesg);
        }

        @Override
        protected void onCancelled(String mesg) {
            super.onCancelled(mesg);
            Log.v("brad", "onCancelled(args)" + mesg);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.v("brad", "onCancelled");
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Log.v("brad", "onProgressUpdate");

            names[Integer.parseInt(values[0])].setText(
                    values[1] + ":" + values[2] +":" + values[3]+":"+values[4]);
        }

        @Override
        protected String doInBackground(String... names) {
            String retMesg = "normal";
            int i=0;
            for (String name : names){
                Log.v("brad", "name = " + name);
                publishProgress("" + i++,
                        name,
                        ""+(int)(Math.random()*49+1),
                        ""+(int)(Math.random()*49+1),
                        ""+(int)(Math.random()*49+1));

                if (isCancelled()){
                    retMesg = "cancel";
                    break;
                }

                try {
                    Thread.sleep(1000);
                }catch (Exception e){

                }
            }
            return retMesg;
        }
    }

}
