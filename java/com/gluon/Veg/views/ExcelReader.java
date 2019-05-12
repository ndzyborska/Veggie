package com.gluon.Veg.views;
import com.gluon.Veg.Veggie;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.*;
import java.io.File;
import org.apache.poi.ss.util.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ExcelReader{
// should use main.class.getresource....

        private String myData = null;
        private Workbook workbook;

        ExcelReader(String myData) throws IOException,InvalidFormatException{
            this.myData = myData;
            workbook = WorkbookFactory.create(new File(myData));

        }

        void Main()throws IOException,InvalidFormatException {
                ExcelReader e = new ExcelReader("C:\\Users\\nadia\\IdeaProjects\\Veggie\\VeggieApp\\src\\main\\resources\\RecipeList\\Recipes.xlsx");
                System.out.println(e.getImage("103", 0));
                System.out.println(e.getImage("104", 0));
                System.out.println(e.getName("103", 0));
        }

   String getImage(String id, int sheetNo)throws IOException,InvalidFormatException{


        DataFormatter dataFormatter = new DataFormatter();
        Sheet sheet=workbook.getSheetAt(sheetNo);
        for(Row row:sheet){
          if(dataFormatter.formatCellValue(row.getCell(0)).equals(id)){
          return dataFormatter.formatCellValue(row.getCell(2));
          }
        }
        return "Image load Error";
   }

   String getName(String id, int sheetNo)throws IOException,InvalidFormatException{
        DataFormatter dataFormatter=new DataFormatter();
        Sheet sheet=workbook.getSheetAt(sheetNo);
        for(Row row:sheet){
          if(dataFormatter.formatCellValue(row.getCell(0)).equals(id)){
            return dataFormatter.formatCellValue(row.getCell(1));
          }
        }

        return "Name load Error";
   }
    int getAmount(int sheetNo)throws IOException,InvalidFormatException{
        DataFormatter dataFormatter = new DataFormatter();
        int lastRowNum = workbook.getSheetAt(sheetNo).getLastRowNum();
        return lastRowNum;

    }

    ArrayList<Ingredients> getFood(String buttonId) {

            System.out.println("EXCEL READER: " + buttonId);

        ArrayList<Ingredients> food = new ArrayList<>();
        DataFormatter d = new DataFormatter();
        Sheet sheet = workbook.getSheet(buttonId);

        for (Row row : sheet) {
            try {
                food.add(new Ingredients(d.formatCellValue(row.getCell(0)),
                        d.formatCellValue(row.getCell(1)),
                        d.formatCellValue(row.getCell(2))));

            } catch (NullPointerException e) {
              System.out.print("Sheet not filled!") ;
            }

        }
        return food;

    }


   void close()throws IOException {
           workbook.close();

   }

// SHOULD RETURN AN ARRAY OR MAP AS ITS INEFFICIENT TO OPEN WORK BOOK TWICE!!! Maybe include it in main??
}

