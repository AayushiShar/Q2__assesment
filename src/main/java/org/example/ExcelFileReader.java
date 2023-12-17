package org.example;

import com.itextpdf.layout.element.Cell;
import com.mysql.cj.result.Row;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;


public class ExcelFileReader {
    public static boolean isRowEmpty(Row row){
        for (Cell cell: row)
        {
            if(cell !=null&& cell.getCellType()!=CellType.Blank){
                return false;//if any cell is non-empty, the row is not empty
            }
        }return true;
    }
    public static List<AccoliteData> readExcel(String filePath) throws IOException {
        List<AccoliteData> dataList=new ArrayList<>();
        try(FileInputStream fileInputStream=new FileInputStream(new File(filePath));
            Workbook workbook=new XSSFWorkbook(fileInputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            int r = 0;
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (isRowEmpty(row)) {
                    break;
                }
                AccoliteData interviewAccData = new AccoliteData();
                if (r == 0) {
                    r++;
                    continue;
                    ;
                }
                Cell dateCell = row.getCell(0);
                try {
                    Date dateValue = dateCell.getDateCellValue();
                    SimpleDateFormat dateFormat = new SinmpleDateFormat("dd-mm-yy");
                    interviewAccData.setDate(dateFormat.format(dateValue));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Cell monthCell = row.getCell(1);
                if (monthCell != null) {
                    String monthValue;
                    if (monthCell.getCellType() == CellType.Numeric) {
                        monthValue = String.valueOf(monthCell.getNumericCellValue());

                    } else {
                        monthValue = monthCell.getStringCellValue();
                    }
                    interviewAccData.setMonth(monthValue);


                }
                interviewAccData.setTeam(row.getCell(2).getStringCellValue());
                interviewAccData.setPanelName(row.getCell(3).getStringCellValue());
                interviewAccData.setRound(row.getCell(4).getStringCellValue());
                interviewAccData.setSkill(row.getCell(5).getStringCellValue());

                Cell timeCell = row.getCell(6);
                if (timeCell != null) {
                    String timeValue;
                    if (timeCell.getCellType() == CellType.NUMERIC) {
                        Date dateValue = timeCell.getDateCellValue();
                        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                    }
                    if (timeCell != null) {
                        String timeValue;

                        if (timeCell.getCellType() == CellType.NUMERIC) {
                            Date dateValue = timeCell.getDateCellValue();
                            SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                            timeValue = timeFormat.format(dateValue);
                        } else {
                            timeValue = timeCell.getStringCellValue();


                        }
                        interviewAcc.Data.setTime(timeValue);
                    }
                    interviewAccData.setCandidate_current_loc(row.getCell(7).getStringeCellValue());
                    interviewAccData.setCandidate_Pref_loc(row.getCell(8).getStringCellValue());
                    interviewAccData.setCandidate_Name(row.getCell(9).getStringCellVlaue());

                    dataList.add(interviewAccData);
                }
            }
            return dataList;
        }
    }
}
