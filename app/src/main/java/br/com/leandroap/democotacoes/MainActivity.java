package br.com.leandroap.democotacoes;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView tvValorIndiceBovespa;
    private TextView tvValorVariacaoBovespa;
    private TextView tvValorCotacaoDolar;
    private TextView tvValorVariacaoDolar;
    private TextView tvValorCotacaoEuro;
    private TextView tvValorVariacaoEuror;
    private Button btAtualizaoCotacao;
    private ProgressDialog progressDialog;
    private DownloadCotacaoTask downloadCotacaoTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvValorIndiceBovespa = (TextView)findViewById(R.id.tvValorIndiceBovespa);
        tvValorVariacaoBovespa = (TextView)findViewById(R.id.tvValorVariacaoBovespa);

        tvValorCotacaoDolar = (TextView)findViewById(R.id.tvValorCotacaoDolar);
        tvValorVariacaoDolar = (TextView)findViewById(R.id.tvValorVariacaoDolar);

        tvValorCotacaoEuro = (TextView)findViewById(R.id.tvValorCotacaoEuro);
        tvValorVariacaoEuror = (TextView)findViewById(R.id.tvValorVariacaoEuro);

        btAtualizaoCotacao = (Button)findViewById(R.id.btAtualizarCotacao);

        btAtualizaoCotacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = ProgressDialog.show(MainActivity.this,
                        getString(R.string.app_name),
                        getString(R.string.msg_atualizando_cotacao)
                );

                downloadCotacaoTask = new DownloadCotacaoTask();
                downloadCotacaoTask.execute();
            }
        });

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (progressDialog != null
                && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void setTvValorIndiceBovespa(TextView tvValorIndiceBovespa) {
        this.tvValorIndiceBovespa = tvValorIndiceBovespa;
    }

    public void setTvValorVariacaoBovespa(TextView tvValorVariacaoBovespa) {
        this.tvValorVariacaoBovespa = tvValorVariacaoBovespa;
    }

    public void setTvValorCotacaoDolar(TextView tvValorCotacaoDolar) {
        this.tvValorCotacaoDolar = tvValorCotacaoDolar;
    }

    public void setTvValorVariacaoDolar(TextView tvValorVariacaoDolar) {
        this.tvValorVariacaoDolar = tvValorVariacaoDolar;
    }

    public void setTvValorCotacaoEuro(TextView tvValorCotacaoEuro) {
        this.tvValorCotacaoEuro = tvValorCotacaoEuro;
    }

    public void setTvValorVariacaoEuror(TextView tvValorVariacaoEuror) {
        this.tvValorVariacaoEuror = tvValorVariacaoEuror;
    }

    private class DownloadCotacaoTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String urlCotacao = "https://drive.google.com/open?id=0B2ZkyOl5v5XJLXNnQVl5OTdWTlU";

            try {
                this.atualizarCotacao(urlCotacao);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void atualizarCotacao(String url) throws IOException {
            URL urlCotacao= null;

            try {
                urlCotacao = new URL(url);
                HttpURLConnection conn = (HttpURLConnection)urlCotacao.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();

                // content está declarado no slide anterior!
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder str = new StringBuilder();

                // lê linha a linha a partir do InputStream
                String line = null;
                while((line = reader.readLine()) != null)
                    str.append(line + "\n");

                // retorno contem todo o conteudo lido
                String retorno = str.toString();

                JSONObject obj = null;
                try {
                    obj = new JSONObject(retorno);

                    for (int i = 0; i < obj.length(); i++){
                        obj.getString("bovespa");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }





            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
    }

}
