/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine.tools;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.BaseAdapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * construit un Objet T depuis un fichier json dont l'adress URL est passé en paramètre
 * Tache asynchrone
 *
 * @author Frédéric RALLO - March 2023
 */
public class HttpAsyncGet<T> {
    private static final String TAG = "fred " + HttpAsyncGet.class.getSimpleName();    //Pour affichage en cas d'erreur
    private final Class<T> clazz;
    private List<T> itemList;
    private final HttpHandler webService;


    public HttpAsyncGet(String url, Class<T> clazz, PostExecuteActivity activity, ProgressDialog progressDialog) {
        super();
        webService = new HttpHandler();
        this.clazz = clazz;
        if (progressDialog != null) onPreExecute(progressDialog);
        Runnable runnable = () -> {
            doInBackGround(url);

            if (activity instanceof BaseAdapter) {
                activity.onPostExecute(getItemResult());
                return;
            }
            activity.runOnUiThread(() -> {
                if (progressDialog != null) progressDialog.dismiss();
                activity.onPostExecute(getItemResult());
            });
        };
        Executors.newSingleThreadExecutor().execute(runnable);
    }

    public void doInBackGround(String urlAddress) {
        // get the jsonStr to parse
        String jsonStr = webService.makeServiceCall(urlAddress);
        if (jsonStr == null) {
            itemList = null;
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            //todo:  itemList = mapper.readValue(jsonStr, new TypeReference<List<T>>(){});   was not possible
            //       the previous line provided List<Object> instead of List<T>
            //       because "l'argument List<T> dans new TypeReference<List<T>>(){} est un type générique non résolu".
            Log.d(TAG, "doInBackGround: " + jsonStr);
            itemList = mapper.readValue(jsonStr, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<T> getItemResult() {
        return itemList;
    }

    public void onPreExecute(ProgressDialog progressDialog) {
        progressDialog.setMessage("Connexion en cours...");
        progressDialog.setCancelable(false);

    }

    static class HttpHandler { //innerClass

        public String makeServiceCall(String reqUrl) {
            String response = null;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(reqUrl).openConnection();
                connection.setRequestMethod("GET");
                // lecture du fichier
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                int responseCode = connection.getResponseCode();
                if (responseCode == 204 || responseCode == 201) {
                    return null;
                }
                response = convertStreamToString(inputStream);
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
            return response;
        }

        //Conversion flux en String
        private String convertStreamToString(InputStream inputStream) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append('\n');
                    Log.e(TAG, line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return stringBuilder.toString();
        }
    }
}