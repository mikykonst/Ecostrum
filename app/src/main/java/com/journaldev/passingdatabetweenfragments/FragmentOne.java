package com.journaldev.passingdatabetweenfragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.journaldev.passingdatabetweenfragments.Model.Price;
import com.journaldev.passingdatabetweenfragments.Model.Product;
import com.journaldev.passingdatabetweenfragments.RecyclerView.ProjectAdapter;
import com.journaldev.passingdatabetweenfragments.XML.XMLGetter;
import com.journaldev.passingdatabetweenfragments.XML.XMLParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FragmentOne extends Fragment {

    SendMessage SM;

    private String XMLStr;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> products;
    private ProgressBar progressBar;
    private Toast toast;
    private ItemsFetch fetch;
    private RecyclerView recyclerView;
    private ProjectAdapter adapter;
    private Spinner chooseTypes;
    TextView textView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_one, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = (ProgressBar) view.findViewById(R.id.waiting_bar);
        recyclerView = (RecyclerView) view.findViewById(R.id.list_of_products);
        chooseTypes = (Spinner) view.findViewById(R.id.type_of_product);
        products = new ArrayList<>();
        fetch = new ItemsFetch();
        fetch.execute();
    }

    interface SendMessage {
        void sendData(Product product);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            SM = (SendMessage) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    public class ItemsFetch extends AsyncTask<Void, String, String> {

        @Override
        protected String doInBackground(Void... params) {
            String str = "";

            XMLGetter getter = new XMLGetter();
            getter.fetchItems(getContext());

            FileInputStream fis = null;
            try {
                fis = getContext().openFileInput("test.xml");
                InputStreamReader is = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(is);
                StringBuilder sb = new StringBuilder();
                XMLStr = bufferedReader.readLine();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return XMLStr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLParser parser = new XMLParser();
            products = parser.parseXML(XMLStr);
            progressBar.setVisibility(View.GONE);

            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setHasFixedSize(true);
            adapter = new ProjectAdapter(products);
            adapter.SetOnItemClickListener(new ProjectAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    choosingSpec(position, products);
                }
            });

            Set<String> set = new TreeSet<>();
            for (int i = 0; i < products.size(); i++) {
                set.add(products.get(i).getType());
            }

            List<String> categories = new ArrayList<>(set);
            categories.add(0, "Вся продукция");

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, categories);
            chooseTypes.setAdapter(spinnerAdapter);
            chooseTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    final List<Product> chProd = new ArrayList<>();
                    if (position == 0) {
                        adapter = new ProjectAdapter(products);
                        adapter.SetOnItemClickListener(new ProjectAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                choosingSpec(position, products);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    } else {
                        for (int i = 0; i < products.size(); i++) {
                            if (chooseTypes.getSelectedItem().toString()
                                    .equalsIgnoreCase(products.get(i).getType())) {
                                chProd.add(products.get(i));
                            }
                        }
                        adapter = new ProjectAdapter(chProd);
                        adapter.SetOnItemClickListener(new ProjectAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                choosingSpec(position, chProd);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    adapter = new ProjectAdapter(products);
                    recyclerView.setAdapter(adapter);
                }
            });
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            toast = Toast.makeText(getContext(), "Обновлено", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void choosingSpec(final int position, final List<Product> arrayOfProducts) {
        final int pos = position;
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Параметры");
        builder.setMessage("Выберете количество до " + arrayOfProducts.get(position).getOstatok() + ":");

        final LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText editText = new EditText(getContext());
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        final RadioGroup priceGroup = new RadioGroup(getContext());

        textView = new TextView(getContext());

        for (Price price : arrayOfProducts.get(position).getPrice()) {
            RadioButton priceBtn = new RadioButton(getContext());
            priceBtn.setText(price.getName());
            priceGroup.addView(priceBtn);
        }

        linearLayout.addView(editText);
        linearLayout.addView(priceGroup);
        linearLayout.addView(textView);

        builder.setView(linearLayout);

        builder.setPositiveButton("Готово", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String string = editText.getText().toString();
                boolean empty = string.equalsIgnoreCase("");
                boolean zero = string.equalsIgnoreCase("0");

                if (!empty && !zero) {
                    if (Integer.parseInt(arrayOfProducts.get(pos).getOstatok()) - Integer.parseInt(editText.getText().toString()) > 0) {
                        arrayOfProducts.get(pos).setOstatok(Integer.toString(Integer.parseInt(arrayOfProducts.get(pos).
                                getOstatok()) - Integer.parseInt(editText.getText().toString())));
                        arrayOfProducts.get(pos).setChoosedCount(editText.getText().toString());
                        for (int i = 0; i < arrayOfProducts.get(position).getPrice().size(); i++) {
                            RadioButton rbtn = (RadioButton) priceGroup.getChildAt(i);
                            if (rbtn.isChecked()) {
                                arrayOfProducts.get(position).setmPrice(arrayOfProducts.get(position).getPrice().get(i).getValue());
                                sendFoo(arrayOfProducts, position);
                            }
                        }
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setTitle("Внимание!");
                        alert.setMessage("Выбирите тип цены!");
                        alert.setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                choosingSpec(position, arrayOfProducts);
                            }
                        });
                        alert.show();
                    }

                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Внимание!");
                    alert.setMessage("Недостаточно товара на складе!");
                    alert.setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            choosingSpec(position, arrayOfProducts);
                        }
                    });
                    alert.show();
                }
            }
        });

        builder.show();
    }

    public void sendFoo(final List<Product> arrayOfProducts, int pos) {
        SM.sendData(arrayOfProducts.get(pos));
        adapter = new ProjectAdapter(arrayOfProducts);
        adapter.SetOnItemClickListener(new ProjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                choosingSpec(position, arrayOfProducts);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}