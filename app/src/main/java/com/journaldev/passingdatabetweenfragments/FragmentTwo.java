package com.journaldev.passingdatabetweenfragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.journaldev.passingdatabetweenfragments.Model.Product;
import com.journaldev.passingdatabetweenfragments.RecyclerView.BagAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FragmentTwo extends Fragment {

    RecyclerView recyclerView;
    List<Product> products;
    RecyclerView.LayoutManager layoutManager;
    BagAdapter adapter;
    TextView totalPrice;
    Button sendToServer;
    JSONObject jsonObject;
    JSONArray jsonArray;
    StringBuffer sb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_two, container, false);
        return rootView;


    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        products = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.bag_list);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        totalPrice = (TextView) view.findViewById(R.id.total_price);

        sendToServer = (Button) view.findViewById(R.id.sendToServer);
        sendToServer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                jsonArray = new JSONArray();
                for (Product product : products) {
                    try {
                        jsonObject = new JSONObject();
                        jsonObject.put("name", product.getName());
                        jsonObject.put("model", product.getModel());
                        jsonObject.put("price", product.getmPrice());
                        jsonObject.put("amount", product.getChoosedCount());
                        jsonObject.put("category", product.getType());
                        jsonObject.put("kod1C", product.getId());
                        jsonArray.put(jsonObject);
                        String xml = XML.toString(jsonArray);
                        String start = "<products>";
                        sb = new StringBuffer(start);
                        sb.append(xml + "</products>");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                SendPost sendPost = new SendPost();
                sendPost.execute();
            }
        });
    }

    protected void displayReceivedData(Product product) {
        double sum = 0;
        products.add(product);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BagAdapter(products);
        recyclerView.setAdapter(adapter);
        for (int i = 0; i < products.size(); i++) {
            sum += Double.parseDouble(products.get(i).getmPrice()) * Double.parseDouble(products.get(i).getChoosedCount());
        }
        DecimalFormat df = new DecimalFormat("#.##");
        totalPrice.setText(df.format(sum) + " грн.");
    }

    public class SendPost extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {
            String urlString = "https://back-ecostrum.herokuapp.com/login";
            String data = sb.toString();
            OutputStream outputStream = null;
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(data);
                writer.flush();
                writer.close();
                outputStream.close();
                urlConnection.connect();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return null;
        }
    }
}