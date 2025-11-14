package api.utilities;

import java.io.BufferedReader;
import java.io.FileReader;

public class CSVUtility {

    private String[][] data;
    private int rowCount;
    private int colCount;

    public CSVUtility(String path) throws Exception {
        loadCSV(path);
    }

    private void loadCSV(String path) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(path));

        String line;
        int rows = 0;

        // Count rows
        while ((line = br.readLine()) != null) {
            rows++;
        }
        br.close();

        // Re-open to read data
        br = new BufferedReader(new FileReader(path));

        this.rowCount = rows;

        // Read first row (header) to count columns
        String header = br.readLine();
        this.colCount = header.split(",").length;

        data = new String[rowCount][colCount];
        data[0] = header.split(",");

        int i = 1;
        while ((line = br.readLine()) != null) {
            data[i] = line.split(",");
            i++;
        }

        br.close();
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public String getCellData(int row, int col) {
        return data[row][col];
    }
}

