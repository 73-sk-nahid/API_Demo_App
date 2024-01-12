package com.example.oct_demo_1;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.oct_demo_1.model.Company;
import com.example.oct_demo_1.utils.SortUtil;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Company_view extends AppCompatActivity {
    //private Button buttonLoad;
    private TableLayout tableLayout;
    private RequestQueue mQueue;
    private ProgressDialog mProgressBar;
    private int PAGE_SIZE = 50;
    private LinearLayout buttonLayout;
    private Button[] buttons;
    private int no_of_pages;
    private ScrollView scrollView;
    private static TableRow tableRowHeader;
    private List<Company> companies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_view);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        tableLayout = findViewById(R.id.table);
        tableLayout.setStretchAllColumns(true);
        mProgressBar = new ProgressDialog(this);

        buttonLayout = findViewById(R.id.btnLay);
        scrollView = findViewById(R.id.scroll_view);
        mQueue = Volley.newRequestQueue(this);

    /*private void jsonParse() {*/
        mProgressBar.setCancelable(true);
        mProgressBar.setMessage("Fetching Data...");
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressBar.show();

        String url = "https://uatezone.octimsbd.com/api/company";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            setTitle("Company (" + String.valueOf(response.length() + ")"));
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject employee = response.getJSONObject(i);
                                String Code = employee.getString("Code");
                                String Name = employee.getString("Name");
                                companies.add(new Company(Code, Name));
                            }
                            createTableHeader();
                            createTable(companies, 0);
                            paginate(buttonLayout, response.length(), PAGE_SIZE, companies);
                            checkBtnBackGroud(0);
                            sortData(buttonLayout, response.length(), PAGE_SIZE, companies);
                            mProgressBar.hide();

                        } catch (JSONException e) {
                            mProgressBar.hide();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    /*});*/
}
    private void paginate(final LinearLayout buttonLayout, final int data_size, final int page_size, final List<Company> companies) {
        no_of_pages = (data_size + page_size - 1) / page_size;
        buttons = new Button[no_of_pages];
        showPageNo(0, no_of_pages);

        for (int i = 0; i < no_of_pages; i++) {
            buttons[i] = new Button(this);
            buttons[i].setBackgroundColor(getResources().getColor(android.R.color.white));
            buttons[i].setText(String.valueOf(i + 1));

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            buttonLayout.addView(buttons[i], lp);

            final int j = i;
            buttons[j].setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                    createTable(companies, j);
                    checkBtnBackGroud(j);
                    showPageNo(j, no_of_pages);
                }
            });
        }
    }

    private void showPageNo(int j, int no_of_pages) {
        Snackbar.make(buttonLayout, "Page " + (j + 1) + " of " + no_of_pages, Snackbar.LENGTH_LONG).setBackgroundTint(getResources().getColor(R.color.transparent)).show();
    }

    private void checkBtnBackGroud(int index) {
        for (int i = 0; i < no_of_pages; i++) {
            if (i == index) {
                buttons[index].setBackgroundResource(R.drawable.cell_shape_square_blue);
            } else {
                buttons[i].setBackground(null);
            }
        }
    }

    private void sortData(final LinearLayout buttonLayout, final int data_size, final int page_size, final List<Company> companies) {
        final TextView tvCode = (TextView) tableRowHeader.getChildAt(0); //Code
        final TextView tvName = (TextView) tableRowHeader.getChildAt(1); //Name
        tvCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortUtil.sortByCode(companies);
                createTable(companies, 0);
                checkBtnBackGroud(0);
            }
        });

        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortUtil.sortByName(companies);
                createTable(companies, 0);
                checkBtnBackGroud(0);
            }
        });
    }

    /*private void createTableRow(String Code, String Name, String Edit, int index) {
        TableRow tableRow = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        tableRow.setLayoutParams(lp);

        TextView textViewCode = new TextView(this);
        TextView textViewName = new TextView(this);
        //_______________________________
        Button editButton = new Button(this);
        Button deleteButton = new Button(this);

        textViewCode.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 0.3f));
        textViewName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.5f));
        //________________________________
        editButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,0));
        deleteButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,0));

        textViewCode.setGravity(Gravity.CENTER);
        textViewName.setGravity(Gravity.CENTER);
        //__________________
        editButton.setGravity(Gravity.CENTER);
        deleteButton.setGravity(Gravity.CENTER);

        textViewName.setMaxLines(3);
        textViewCode.setMaxLines(2);

        textViewCode.setPadding(2, 10, 2, 10);
        textViewName.setPadding(2, 10, 2, 10);

        textViewCode.setText(Code);
        textViewName.setText(Name);
        //_______________________________
        //editButton.setText(Edit);
        //deleteButton.setText(Delete);

        textViewCode.setBackgroundResource(R.drawable.cell_shape_grey);
        textViewName.setBackgroundResource(R.drawable.cell_shape_white);
        //_________________________________
        //editButton.setBackgroundResource(R.drawable.edit_btn);
        //deleteButton.setBackgroundResource(R.drawable.delete_btn);
        // Set vector drawables as background for buttons
        editButton.setBackgroundResource(R.drawable.edit);
        deleteButton.setBackgroundResource(R.drawable.delete);

        tableRow.addView(textViewCode);
        tableRow.addView(textViewName);
        //________________________________
        tableRow.addView(editButton);
        tableRow.addView(deleteButton);

        if (index == -1) {
            tableRowHeader = tableRow;
            textViewCode.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) getResources().getDimension(R.dimen.font_size_small));
            textViewName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) getResources().getDimension(R.dimen.font_size_small));
            //_________________________________________________
            editButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) getResources().getDimension(R.dimen.font_size_small));
            deleteButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) getResources().getDimension(R.dimen.font_size_small));

            textViewCode.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_by_alpha_black, 0);
            textViewName.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_sort_by_alpha_black, 0);

            textViewCode.setBackgroundResource(R.drawable.cell_shape_blue);
            textViewName.setBackgroundResource(R.drawable.cell_shape_blue);
            //_______________________________________________________
            editButton.setBackgroundResource(R.drawable.cell_shape_blue);
            deleteButton.setBackgroundResource(R.drawable.cell_shape_blue);
        }
        if (index  >= 0){
            setMarginsForView(editButton,2, 3, 2, 2);
            setMarginsForView(deleteButton,2, 3, 2, 2);
            //_______________________________
            editButton.setTextColor(Color.WHITE); // Set text color
            editButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            deleteButton.setTextColor(Color.WHITE); // Set text color
            deleteButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
        tableLayout.addView(tableRow, index + 1);
    }*/

    private void createTableRow(String Code, String Name, String Operations, int index) {
        TableRow tableRow = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        tableRow.setLayoutParams(lp);

        TextView textViewCode = new TextView(this);
        TextView textViewName = new TextView(this);
        TextView textViewOperations = new TextView(this);
        //_______________________________
        //Button editButton = new Button(this);
        //Button deleteButton = new Button(this);

        textViewCode.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 0.3f));
        textViewName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.5f));
        textViewOperations.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
        //________________________________
        //editButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0));
        //deleteButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0));

        textViewCode.setGravity(Gravity.CENTER);
        textViewName.setGravity(Gravity.CENTER);
        textViewOperations.setGravity(Gravity.CENTER);
        //__________________
        //editButton.setGravity(Gravity.CENTER);
        //deleteButton.setGravity(Gravity.CENTER);

        textViewName.setMaxLines(4);
        textViewCode.setMaxLines(2);

        textViewCode.setPadding(2, 10, 2, 10);
        textViewName.setPadding(2, 10, 2, 10);
        textViewOperations.setPadding(2, 10, 2, 10);

        textViewCode.setText(Code);
        textViewName.setText(Name);
        textViewOperations.setText(Operations); // Set "Operations" text
        //_______________________________
        //editButton.setText(Edit);
        //deleteButton.setText(Delete);

        textViewCode.setBackgroundResource(R.drawable.cell_shape_grey);
        textViewName.setBackgroundResource(R.drawable.cell_shape_white);
        textViewName.setBackgroundResource(R.drawable.cell_shape_white);
        //textViewOperations.setBackgroundResource(R.drawable.marge);

        //_________________________________
        //editButton.setBackgroundResource(R.drawable.edit_btn);
        //deleteButton.setBackgroundResource(R.drawable.delete_btn);
        // Set vector drawables as background for buttons
        //editButton.setBackgroundResource(R.drawable.edit);
        //deleteButton.setBackgroundResource(R.drawable.delete);
        tableRow.addView(textViewCode);
        tableRow.addView(textViewName);
        textViewOperations.setBackgroundResource(R.drawable.merge);
        tableRow.addView(textViewOperations);
        //tableRow.addView(textViewOperations);
        //________________________________
        //tableRow.addView(editButton);
        //tableRow.addView(deleteButton);

        if (index == -1) {
            tableRowHeader = tableRow;
            textViewCode.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) getResources().getDimension(R.dimen.font_size_small));
            textViewName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) getResources().getDimension(R.dimen.font_size_small));
            textViewOperations.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) getResources().getDimension(R.dimen.font_size_small));
            //_________________________________________________
            //editButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) getResources().getDimension(R.dimen.font_size_small));
            //deleteButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) getResources().getDimension(R.dimen.font_size_small));

            textViewCode.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_by_alpha_black, 0);
            textViewName.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_sort_by_alpha_black, 0);

            textViewCode.setBackgroundResource(R.drawable.cell_shape_blue);
            textViewName.setBackgroundResource(R.drawable.cell_shape_blue);
            textViewOperations.setBackgroundResource(R.drawable.cell_shape_blue);
            //_______________________________________________________
            //editButton.setBackgroundResource(R.drawable.cell_shape_blue);
            //deleteButton.setBackgroundResource(R.drawable.cell_shape_blue);
        }
        if (index >= 0) {
            //setMarginsForView(editButton, 2, 3, 2, 2);
            //setMarginsForView(deleteButton, 2, 3, 2, 2);
            //_______________________________
            //editButton.setTextColor(Color.WHITE); // Set text color
            //editButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            //deleteButton.setTextColor(Color.WHITE); // Set text color
            //deleteButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

            //textViewOperations.
            String originalText = Name.toString();
            int maxCharactersPerLine = 40; // set your desired maximum characters per line

            StringBuilder modifiedText = new StringBuilder();
            int charCount = 0;

            for (char c : originalText.toCharArray()) {
                modifiedText.append(c);
                charCount++;

                if (charCount >= maxCharactersPerLine) {
                    modifiedText.append("\n"); // add newline after reaching the maximum characters per line
                    charCount = 0; // reset character count for the next line
                }
            }

            textViewName.setText(modifiedText.toString());

        }
        tableLayout.addView(tableRow, index + 1);
    }


    private void setMarginsForView(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(left, top, right, bottom);
        view.setLayoutParams(layoutParams);
    }

    private void createTableHeader() {
        tableLayout.removeAllViews();
        //createTableRow("Code", "Name","Edit","Delete", -1);
        createTableRow("Code", "Name","Action", -1);
    }

    private void createTable(List<Company> companies, int page) {
        tableLayout.removeAllViews();
        tableLayout.addView(tableRowHeader);
        // data rows
        for (int i = 0, j = page * 50; j < companies.size() && i < 50; i++, j++) {
            createTableRow(
                    companies.get(j).getCode(),
                    companies.get(j).getName(),
                    "",
                    i

            );
        }

    }
}