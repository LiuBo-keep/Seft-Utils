package seftutlis.execlutil.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName ExcelUtil
 * @Description TODO
 * @Author liubo
 * @Date 2021/1/5 14:53
 */
public class ExcelUtil<T> {

    /**
     * Excel类型
     * HSSFWorkbook:是操作Excel2003以前（包括2003）的版本，扩展名是.xls
     * XSSFWorkbook:是操作Excel2007的版本，扩展名是.xlsx
     */
    private static final String EXCEL_TYPE_XLS = "application/vnd.ms-excel";
    private static final String EXCEL_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    /**
     * @return org.apache.poi.ss.usermodel.Workbook
     * @Description TODO 生成Excel
     * @Param [excelType excel当前版本, titles 首行小标题, content 具体数据]
     * @date 2021/1/5 14:33
     * @auther liubo
     */
    @SuppressWarnings("all")
    public Workbook getExcel(String excelType, Object titles, List<T> content) throws Exception {
        Workbook workbook = null;
        switch (excelType) {
            case EXCEL_TYPE_XLS:
                workbook = new HSSFWorkbook();
                break;
            case EXCEL_TYPE_XLSX:
                workbook = new XSSFWorkbook();
                break;
            default:
                throw new Exception("不存在该版本");
        }
        
        // 样式
        CellStyle cellStyle = workbook != null ? workbook.createCellStyle() : null;
        Font font = workbook != null ? workbook.createFont() : null;
        if (null != cellStyle && null != font) {
            font.setFontName("宋体");
            font.setColor(Font.COLOR_NORMAL);
            font.setFontHeightInPoints((short) 12);
            cellStyle.setFont(font);
        }
        
        Sheet sheet = workbook.createSheet();
        try {
            // 小标题
            if (Objects.nonNull(titles)) {
                Class<?> titlesClass = titles.getClass();
                Field[] declaredFields = titlesClass.getDeclaredFields();
                if (null != declaredFields && declaredFields.length > 0) {
                    Row row = sheet.createRow(0);
                    for (int i = 0; i < declaredFields.length; i++) {
                        Field field = declaredFields[i];
                        field.setAccessible(true);
                        row.createCell(i).setCellValue((String) field.get(titles));
                    }
                }
            }
            // 具体内容
            if (null != content && content.size() > 0) {
                for (int i = 0; i < content.size(); i++) {
                    Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                    Class<?> contentsClass = content.get(i).getClass();
                    Field[] fields = contentsClass.getDeclaredFields();
                    if (null != fields && fields.length > 0) {
                        for (int j = 0; j < fields.length; j++) {
                            Field field = fields[j];
                            field.setAccessible(true);
                            row.createCell(j).setCellValue((String) field.get(content.get(i)));
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new IllegalAccessException(e.getMessage());
        }
        return workbook;
    }
}
