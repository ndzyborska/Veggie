package com.gluon.Veg.views;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.IOException;

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


   void close()throws IOException,InvalidFormatException {
           workbook.close();
   }

// SHOULD RETURN AN ARRAY OR MAP AS ITS INEFFICIENT TO OPEN WORK BOOK TWICE!!! Maybe include it in main??
}

