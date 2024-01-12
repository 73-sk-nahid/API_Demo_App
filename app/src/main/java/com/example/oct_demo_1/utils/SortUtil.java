package com.example.oct_demo_1.utils;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.example.oct_demo_1.model.Company;

public class SortUtil implements Comparator<Company> {
    private static int SORT_FACTOR = -1;
    private static boolean sort_asc = true;

    public static void sortByCode(List<Company> data) {
        if (SORT_FACTOR == 0 && sort_asc == false) {
            Collections.sort(data, Collections.reverseOrder(new SortUtil()));
            sort_asc = true;
        } else {
            SORT_FACTOR = 0;
            Collections.sort(data, new SortUtil());
            sort_asc = false;
        }
    }

    public static void sortByName(List<Company> data) {
        if (SORT_FACTOR == 1 && sort_asc == false) {
            Collections.sort(data, Collections.reverseOrder(new SortUtil()));
            sort_asc = true;
        } else {
            SORT_FACTOR = 1;
            Collections.sort(data, new SortUtil());
            sort_asc = false;
        }
    }


    @Override
    public int compare(Company a1, Company a2) {
        switch (SORT_FACTOR) {
            case 0:
            default:
                return a1.getCode().compareToIgnoreCase(a2.getCode());
            case 1:
                return a1.getName().compareToIgnoreCase(a2.getName());
        }
    }
}
