package api.utilities;

import org.testng.annotations.DataProvider;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProviders {

    // ✅ Always use src/test/resources path
    String path = System.getProperty("user.dir")
            + "/userdata/userdata1.csv";

    @DataProvider(name = "Data")
    public Object[][] getAllData() throws IOException {
        List<String[]> rows = readCSV(path);

        int rowCount = rows.size() - 1;
        int colCount = rows.get(0).length;

        Object[][] data = new Object[rowCount][colCount];

        for (int i = 1; i < rows.size(); i++) {
            data[i - 1] = rows.get(i);
        }

        return data;
    }

    private List<String[]> readCSV(String filePath) throws IOException {

        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        }

        return data;
    }
}





