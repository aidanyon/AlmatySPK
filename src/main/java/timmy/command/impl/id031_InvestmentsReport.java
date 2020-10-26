package timmy.command.impl;

import timmy.command.Command;
import timmy.entity.custom.Application;
import timmy.entity.custom.Investments;
import timmy.entity.custom.Operators;
import timmy.entity.custom.Question;
import timmy.utils.Const;
import timmy.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Slf4j
public class id031_InvestmentsReport extends Command {

    private List<Investments>  allInvestments;
    private List<Application>   allApplication1;
    private List<Application>   allApplication2;
    private List<Application>   allApplication3;
    private List<Application>   allApplication4;
    private List<Application>   allApplication5;
    private List<Application>   allApplication6;
    private List<Operators>  allOperators1;
    private List<Operators>  allOperators2;
    private List<Operators>  allOperators3;
    private List<Operators>  allOperators4;
    private List<Operators>  allOperators5;
    private List<Operators>  allOperators6;
    private List<Question>  allQuestion;
    private int         count;
    private int         messagePreviewReport;

    @Override
    public boolean execute()    throws TelegramApiException {
        if (!isAdmin() && !isMainAdmin()) {
            sendMessage(Const.NO_ACCESS);
            return EXIT;
        }
        if (hasMessageText()) {
            count       = investmentsDao.count();
            allInvestments    = investmentsDao.getAll();
            allApplication1    =applicationDao.getAll1();
            allApplication2    =applicationDao.getAll2();
            allApplication3    =applicationDao.getAll3();
            allApplication4    =applicationDao.getAll4();
            allApplication5    =applicationDao.getAll5();
            allApplication6    =applicationDao.getAll6();
            allOperators1       =operatorsDao.getAll1();
            allOperators2       =operatorsDao.getAll2();
            allOperators3       =operatorsDao.getAll3();
            allOperators4       =operatorsDao.getAll4();
            allOperators5       =operatorsDao.getAll5();
            allOperators6       =operatorsDao.getAll6();
            allQuestion         =questionDao.getAll();

            if (count == 0) {
                sendMessage(Const.REGISTRATION_USERS_NOT_FOUND_MESSAGE);
                return EXIT;
            }
            messagePreviewReport = sendMessage(String.format(getText(Const.USERS_REPORT_DOING_MESSAGE), count));
            new Thread(() -> {
                try {
                    sendReport();
                } catch (TelegramApiException e) {
                    log.error("Can't send report", e);
                    try {
                        sendMessage("Ошибка отправки списка");
                    } catch (TelegramApiException ex) {
                        log.error("Can't send message", ex);
                    }
                }
            }).start();
        }
        return COMEBACK;
    }

    private void        sendReport() throws TelegramApiException {
        int total           = count;
        XSSFWorkbook wb     = new XSSFWorkbook();
        Sheet sheets        = wb.createSheet("Инвестировать в проекты г.Алматы");
        Sheet sheets1        = wb.createSheet("Арендовать место на ярмарке(Оставить заявку)");
        Sheet sheets2        = wb.createSheet("Арендовать место на ярмарке(Получить консультацию)");
        Sheet sheets3        = wb.createSheet("Арендовать Павильон(НОТы)(Получить консультацию)");
        Sheet sheets5        = wb.createSheet("Арендовать помещение в промпарке(Оставить заявку)");
        Sheet sheets6        = wb.createSheet("Получить консультацию(Арендовать помещение в промпарке)");
        Sheet sheets7        = wb.createSheet("Разместить крупное производство(Оставить заявку)");
        Sheet sheets8        = wb.createSheet("Арендовать активы СПК(Подать заявку)");
        Sheet sheets9        = wb.createSheet("Арендовать активы СПК(Получить консультацию)");
        Sheet sheets10        = wb.createSheet("Дочерние компании. Получить кредит(от 500тыс. до 55млн.)");
        Sheet sheets11        = wb.createSheet("Получить кредит(от 20млн. до 500млн.)");
        Sheet sheets12        = wb.createSheet("Часто задаваемые вопросы");

        // -------------------------Стиль ячеек-------------------------
        BorderStyle thin            = BorderStyle.THIN;
        short black                 = IndexedColors.BLACK.getIndex();
        XSSFCellStyle style         = wb.createCellStyle();
        style.setWrapText           (true);
        style.setAlignment          (HorizontalAlignment.CENTER);
        style.setVerticalAlignment  (VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setBorderTop          (thin);
        style.setBorderBottom       (thin);
        style.setBorderRight        (thin);
        style.setBorderLeft         (thin);
        style.setTopBorderColor     (black);
        style.setRightBorderColor   (black);
        style.setBottomBorderColor  (black);
        style.setLeftBorderColor    (black);
        BorderStyle tittle              = BorderStyle.MEDIUM;
        XSSFCellStyle styleTitle        = wb.createCellStyle();
        styleTitle.setWrapText          (true);
        styleTitle.setAlignment         (HorizontalAlignment.CENTER);
        styleTitle.setVerticalAlignment (VerticalAlignment.CENTER);
        styleTitle.setBorderTop         (tittle);
        styleTitle.setBorderBottom      (tittle);
        styleTitle.setBorderRight       (tittle);
        styleTitle.setBorderLeft        (tittle);
        styleTitle.setTopBorderColor    (black);
        styleTitle.setRightBorderColor  (black);
        styleTitle.setBottomBorderColor (black);
        styleTitle.setLeftBorderColor   (black);
        style.setFillForegroundColor    (new XSSFColor(new Color(0, 52, 94)));
        //--------------------------------------------------------------------
        //ИНВЕСТИРОВАТЬ В ПРОЕКТЫ Г.АЛМАТЫ
        Sheet sheet                     = wb.getSheetAt(0);
        int rowIndex    = 0;
        int CellIndex   = 0;
        sheets  .createRow(rowIndex) .createCell(CellIndex)  .setCellValue("№");
        sheet   .getRow(rowIndex)    .getCell(CellIndex)     .setCellStyle(styleTitle);
        sheets  .getRow(rowIndex)    .createCell(++CellIndex).setCellValue("ФИО");
        sheet   .getRow(rowIndex)    .getCell(CellIndex)     .setCellStyle(styleTitle);
        sheets  .getRow(rowIndex)    .createCell(++CellIndex).setCellValue("Контакты");
        sheet   .getRow(rowIndex)    .getCell(CellIndex)     .setCellStyle(styleTitle);
        sheets  .getRow(rowIndex)    .createCell(++CellIndex).setCellValue("E-mail");
        sheet   .getRow(rowIndex)    .getCell(CellIndex)     .setCellStyle(styleTitle);
        sheets  .getRow(rowIndex)    .createCell(++CellIndex).setCellValue("Название компании");
        sheet   .getRow(rowIndex)    .getCell(CellIndex)     .setCellStyle(styleTitle);
        sheets  .getRow(rowIndex)    .createCell(++CellIndex).setCellValue("Отрасль вашей компании");
        sheet   .getRow(rowIndex)    .getCell(CellIndex)     .setCellStyle(styleTitle);
        sheets  .getRow(rowIndex)    .createCell(++CellIndex).setCellValue("Поле для дополнительных комментариев");
        sheet   .getRow(rowIndex)    .getCell(CellIndex)     .setCellStyle(styleTitle);

        for (Investments entity: allInvestments) {
            CellIndex = 0;
            sheets.createRow(++rowIndex).createCell(CellIndex).setCellValue(entity.getId());
            sheet.getRow(rowIndex).getCell(CellIndex).setCellStyle(style);
            sheets.getRow(rowIndex).createCell(++CellIndex).setCellValue(entity.getFullName());
            sheet.getRow(rowIndex).getCell(CellIndex).setCellStyle(style);
            sheets.getRow(rowIndex).createCell(++CellIndex).setCellValue(entity.getContact());
            sheet.getRow(rowIndex).getCell(CellIndex).setCellStyle(style);
            sheets.getRow(rowIndex).createCell(++CellIndex).setCellValue(entity.getEmail());
            sheet.getRow(rowIndex).getCell(CellIndex).setCellStyle(style);
            sheets.getRow(rowIndex).createCell(++CellIndex).setCellValue(entity.getCompany());
            sheet.getRow(rowIndex).getCell(CellIndex).setCellStyle(style);
            sheets.getRow(rowIndex).createCell(++CellIndex).setCellValue(entity.getDepartment());
            sheet.getRow(rowIndex).getCell(CellIndex).setCellStyle(style);
            sheets.getRow(rowIndex).createCell(++CellIndex).setCellValue(entity.getComment());
            sheet.getRow(rowIndex).getCell(CellIndex).setCellStyle(style);
        }
        //АРЕНДОВАТЬ МЕСТО НА ЯРМАРКЕ(ОСТАВИТЬ ЗАЯВКУ) APPLICATION ID=2
        Sheet sheet1                     = wb.getSheetAt(1);
        int rowIndex1    = 0;
        int CellIndex1   = 0;
        sheets1  .createRow(rowIndex1) .createCell(CellIndex1)  .setCellValue("№");
        sheet1   .getRow(rowIndex1)    .getCell(CellIndex1)     .setCellStyle(styleTitle);
        sheets1  .getRow(rowIndex1)    .createCell(++CellIndex1).setCellValue("ФИО");
        sheet1   .getRow(rowIndex1)    .getCell(CellIndex1)     .setCellStyle(styleTitle);
        sheets1  .getRow(rowIndex1)    .createCell(++CellIndex1).setCellValue("Контакты");
        sheet1   .getRow(rowIndex1)    .getCell(CellIndex1)     .setCellStyle(styleTitle);
        sheets1  .getRow(rowIndex1)    .createCell(++CellIndex1).setCellValue("E-mail");
        sheet1   .getRow(rowIndex1)    .getCell(CellIndex1)     .setCellStyle(styleTitle);
        sheets1  .getRow(rowIndex1)    .createCell(++CellIndex1).setCellValue("Название компании");
        sheet1   .getRow(rowIndex1)    .getCell(CellIndex1)     .setCellStyle(styleTitle);
        sheets1  .getRow(rowIndex1)    .createCell(++CellIndex1).setCellValue("Отрасль вашей компании");
        sheet1   .getRow(rowIndex1)    .getCell(CellIndex1)     .setCellStyle(styleTitle);
        sheets1  .getRow(rowIndex1)    .createCell(++CellIndex1).setCellValue("Реализуемая продукция");
        sheet1   .getRow(rowIndex1)    .getCell(CellIndex1)     .setCellStyle(styleTitle);
        sheets1  .getRow(rowIndex1)    .createCell(++CellIndex1).setCellValue("Поле для дополнительных комментариев");
        sheet1   .getRow(rowIndex1)    .getCell(CellIndex1)     .setCellStyle(styleTitle);

        for (Application entity: allApplication2) {
            CellIndex1 = 0;
            sheets1.createRow(++rowIndex1).createCell(CellIndex1).setCellValue(entity.getId());
            sheet1.getRow(rowIndex1).getCell(CellIndex1).setCellStyle(style);
            sheets1.getRow(rowIndex1).createCell(++CellIndex1).setCellValue(entity.getFullName());
            sheet1.getRow(rowIndex1).getCell(CellIndex1).setCellStyle(style);
            sheets1.getRow(rowIndex1).createCell(++CellIndex1).setCellValue(entity.getPhoneNumber());
            sheet1.getRow(rowIndex1).getCell(CellIndex1).setCellStyle(style);
            sheets1.getRow(rowIndex1).createCell(++CellIndex1).setCellValue(entity.getEmail());
            sheet1.getRow(rowIndex1).getCell(CellIndex1).setCellStyle(style);
            sheets1.getRow(rowIndex1).createCell(++CellIndex1).setCellValue(entity.getCompany());
            sheet1.getRow(rowIndex1).getCell(CellIndex1).setCellStyle(style);
            sheets1.getRow(rowIndex1).createCell(++CellIndex1).setCellValue(entity.getDepartment());
            sheet1.getRow(rowIndex1).getCell(CellIndex1).setCellStyle(style);
            sheets1.getRow(rowIndex1).createCell(++CellIndex1).setCellValue(entity.getRequest());
            sheet1.getRow(rowIndex1).getCell(CellIndex1).setCellStyle(style);
            sheets1.getRow(rowIndex1).createCell(++CellIndex1).setCellValue(entity.getComment());
            sheet1.getRow(rowIndex1).getCell(CellIndex1).setCellStyle(style);
        }

        //АРЕНДОВАТЬ МЕСТО НА ЯРМАРКЕ(ПОЛУЧИТЬ КОНСУЛЬТАЦИЮ) OPERATORS ID=3
        Sheet sheet2                     = wb.getSheetAt(2);
        int rowIndex2    = 0;
        int CellIndex2   = 0;
        sheets2  .createRow(rowIndex2) .createCell(CellIndex2)  .setCellValue("№");
        sheet2   .getRow(rowIndex2)    .getCell(CellIndex2)     .setCellStyle(styleTitle);
        sheets2  .getRow(rowIndex2)    .createCell(++CellIndex2).setCellValue("ФИО");
        sheet2   .getRow(rowIndex2)    .getCell(CellIndex2)     .setCellStyle(styleTitle);
        sheets2  .getRow(rowIndex2)    .createCell(++CellIndex2).setCellValue("Контакты");
        sheet2   .getRow(rowIndex2)    .getCell(CellIndex2)     .setCellStyle(styleTitle);
        sheets2  .getRow(rowIndex2)    .createCell(++CellIndex2).setCellValue("E-mail");
        sheet2   .getRow(rowIndex2)    .getCell(CellIndex2)     .setCellStyle(styleTitle);
        sheets2  .getRow(rowIndex2)    .createCell(++CellIndex2).setCellValue("Название компании");
        sheet2   .getRow(rowIndex2)    .getCell(CellIndex2)     .setCellStyle(styleTitle);
        sheets2  .getRow(rowIndex2)    .createCell(++CellIndex2).setCellValue("Отрасль вашей компании");
        sheet2   .getRow(rowIndex2)    .getCell(CellIndex2)     .setCellStyle(styleTitle);
        sheets2  .getRow(rowIndex2)    .createCell(++CellIndex2).setCellValue("Ваш вопрос для консультации");
        sheet2   .getRow(rowIndex2)    .getCell(CellIndex2)     .setCellStyle(styleTitle);
        sheets2  .getRow(rowIndex2)    .createCell(++CellIndex2).setCellValue("Поле для дополнительных комментариев");
        sheet2   .getRow(rowIndex2)    .getCell(CellIndex2)     .setCellStyle(styleTitle);

        for (Operators entity: allOperators3) {
            CellIndex2 = 0;
            sheets2.createRow(++rowIndex2).createCell(CellIndex2).setCellValue(entity.getId());
            sheet2.getRow(rowIndex2).getCell(CellIndex2).setCellStyle(style);
            sheets2.getRow(rowIndex2).createCell(++CellIndex2).setCellValue(entity.getFullName());
            sheet2.getRow(rowIndex2).getCell(CellIndex2).setCellStyle(style);
            sheets2.getRow(rowIndex2).createCell(++CellIndex2).setCellValue(entity.getPhoneNumber());
            sheet2.getRow(rowIndex2).getCell(CellIndex2).setCellStyle(style);
            sheets2.getRow(rowIndex2).createCell(++CellIndex2).setCellValue(entity.getEmail());
            sheet2.getRow(rowIndex2).getCell(CellIndex2).setCellStyle(style);
            sheets2.getRow(rowIndex2).createCell(++CellIndex2).setCellValue(entity.getCompany());
            sheet2.getRow(rowIndex2).getCell(CellIndex2).setCellStyle(style);
            sheets2.getRow(rowIndex2).createCell(++CellIndex2).setCellValue(entity.getDepartment());
            sheet2.getRow(rowIndex2).getCell(CellIndex2).setCellStyle(style);
            sheets2.getRow(rowIndex2).createCell(++CellIndex2).setCellValue(entity.getQuestion());
            sheet2.getRow(rowIndex2).getCell(CellIndex2).setCellStyle(style);
            sheets2.getRow(rowIndex2).createCell(++CellIndex2).setCellValue(entity.getComment());
            sheet2.getRow(rowIndex2).getCell(CellIndex2).setCellStyle(style);
        }

        //АРЕНДОВАТЬ ПАВИЛЬОН(НОТы)(ПОЛУЧИТЬ КОНСУЛЬТАЦИЮ) OPERATORS ID=2
        Sheet sheet3                     = wb.getSheetAt(3);
        int rowIndex3    = 0;
        int CellIndex3   = 0;
        sheets3  .createRow(rowIndex3) .createCell(CellIndex3)  .setCellValue("№");
        sheet3   .getRow(rowIndex3)    .getCell(CellIndex3)     .setCellStyle(styleTitle);
        sheets3  .getRow(rowIndex3)    .createCell(++CellIndex3).setCellValue("ФИО");
        sheet3   .getRow(rowIndex3)    .getCell(CellIndex3)     .setCellStyle(styleTitle);
        sheets3  .getRow(rowIndex3)    .createCell(++CellIndex3).setCellValue("Контакты");
        sheet3   .getRow(rowIndex3)    .getCell(CellIndex3)     .setCellStyle(styleTitle);
        sheets3  .getRow(rowIndex3)    .createCell(++CellIndex3).setCellValue("E-mail");
        sheet3   .getRow(rowIndex3)    .getCell(CellIndex3)     .setCellStyle(styleTitle);
        sheets3  .getRow(rowIndex3)    .createCell(++CellIndex3).setCellValue("Название компании");
        sheet3   .getRow(rowIndex3)    .getCell(CellIndex3)     .setCellStyle(styleTitle);
        sheets3  .getRow(rowIndex3)    .createCell(++CellIndex3).setCellValue("Отрасль вашей компании");
        sheet3   .getRow(rowIndex3)    .getCell(CellIndex3)     .setCellStyle(styleTitle);
        sheets3  .getRow(rowIndex3)    .createCell(++CellIndex3).setCellValue("Ваш вопрос для консультации");
        sheet3   .getRow(rowIndex3)    .getCell(CellIndex3)     .setCellStyle(styleTitle);
        sheets3  .getRow(rowIndex3)    .createCell(++CellIndex3).setCellValue("Поле для дополнительных комментариев");
        sheet3   .getRow(rowIndex3)    .getCell(CellIndex3)     .setCellStyle(styleTitle);

        for (Operators entity: allOperators2) {
            CellIndex3 = 0;
            sheets3.createRow(++rowIndex3).createCell(CellIndex3).setCellValue(entity.getId());
            sheet3.getRow(rowIndex3).getCell(CellIndex3).setCellStyle(style);
            sheets3.getRow(rowIndex3).createCell(++CellIndex3).setCellValue(entity.getFullName());
            sheet3.getRow(rowIndex3).getCell(CellIndex3).setCellStyle(style);
            sheets3.getRow(rowIndex3).createCell(++CellIndex3).setCellValue(entity.getPhoneNumber());
            sheet3.getRow(rowIndex3).getCell(CellIndex3).setCellStyle(style);
            sheets3.getRow(rowIndex3).createCell(++CellIndex3).setCellValue(entity.getEmail());
            sheet3.getRow(rowIndex3).getCell(CellIndex3).setCellStyle(style);
            sheets3.getRow(rowIndex3).createCell(++CellIndex3).setCellValue(entity.getCompany());
            sheet3.getRow(rowIndex3).getCell(CellIndex3).setCellStyle(style);
            sheets3.getRow(rowIndex3).createCell(++CellIndex3).setCellValue(entity.getDepartment());
            sheet3.getRow(rowIndex3).getCell(CellIndex3).setCellStyle(style);
            sheets3.getRow(rowIndex3).createCell(++CellIndex3).setCellValue(entity.getQuestion());
            sheet3.getRow(rowIndex3).getCell(CellIndex3).setCellStyle(style);
            sheets3.getRow(rowIndex3).createCell(++CellIndex3).setCellValue(entity.getComment());
            sheet3.getRow(rowIndex3).getCell(CellIndex3).setCellStyle(style);
        }

        //АРЕНДОВАТЬ ПОМЕЩЕНИЕ В ПРОМПАРКЕ(ОСТАВИТЬ ЗАЯВКУ) APPLICATION ID=6
        Sheet sheet4                     = wb.getSheetAt(4);
        int rowIndex4    = 0;
        int CellIndex4   = 0;
        sheets5.createRow(rowIndex4) .createCell(CellIndex4)  .setCellValue("№");
        sheet4   .getRow(rowIndex4)    .getCell(CellIndex4)     .setCellStyle(styleTitle);
        sheets5  .getRow(rowIndex4)    .createCell(++CellIndex4).setCellValue("ФИО");
        sheet4   .getRow(rowIndex4)    .getCell(CellIndex4)     .setCellStyle(styleTitle);
        sheets5  .getRow(rowIndex4)    .createCell(++CellIndex4).setCellValue("Контакты");
        sheet4   .getRow(rowIndex4)    .getCell(CellIndex4)     .setCellStyle(styleTitle);
        sheets5  .getRow(rowIndex4)    .createCell(++CellIndex4).setCellValue("E-mail");
        sheet4   .getRow(rowIndex4)    .getCell(CellIndex4)     .setCellStyle(styleTitle);
        sheets5  .getRow(rowIndex4)    .createCell(++CellIndex4).setCellValue("Название компании");
        sheet4   .getRow(rowIndex4)    .getCell(CellIndex4)     .setCellStyle(styleTitle);
        sheets5  .getRow(rowIndex4)    .createCell(++CellIndex4).setCellValue("Отрасль вашей компании");
        sheet4   .getRow(rowIndex4)    .getCell(CellIndex4)     .setCellStyle(styleTitle);
        sheets5  .getRow(rowIndex4)    .createCell(++CellIndex4).setCellValue("Какой объект Вас заинтересовал");
        sheet4   .getRow(rowIndex4)    .getCell(CellIndex4)     .setCellStyle(styleTitle);
        sheets5  .getRow(rowIndex4)    .createCell(++CellIndex4).setCellValue("Поле для дополнительных комментариев");
        sheet4   .getRow(rowIndex4)    .getCell(CellIndex4)     .setCellStyle(styleTitle);

        for (Application entity: allApplication6) {
            CellIndex4 = 0;
            sheets5.createRow(++rowIndex4).createCell(CellIndex4).setCellValue(entity.getId());
            sheet4.getRow(rowIndex4).getCell(CellIndex4).setCellStyle(style);
            sheets5.getRow(rowIndex4).createCell(++CellIndex4).setCellValue(entity.getFullName());
            sheet4.getRow(rowIndex4).getCell(CellIndex4).setCellStyle(style);
            sheets5.getRow(rowIndex4).createCell(++CellIndex4).setCellValue(entity.getPhoneNumber());
            sheet4.getRow(rowIndex4).getCell(CellIndex4).setCellStyle(style);
            sheets5.getRow(rowIndex4).createCell(++CellIndex4).setCellValue(entity.getEmail());
            sheet4.getRow(rowIndex4).getCell(CellIndex4).setCellStyle(style);
            sheets5.getRow(rowIndex4).createCell(++CellIndex4).setCellValue(entity.getCompany());
            sheet4.getRow(rowIndex4).getCell(CellIndex4).setCellStyle(style);
            sheets5.getRow(rowIndex4).createCell(++CellIndex4).setCellValue(entity.getDepartment());
            sheet4.getRow(rowIndex4).getCell(CellIndex4).setCellStyle(style);
            sheets5.getRow(rowIndex4).createCell(++CellIndex4).setCellValue(entity.getRequest());
            sheet4.getRow(rowIndex4).getCell(CellIndex4).setCellStyle(style);
            sheets5.getRow(rowIndex4).createCell(++CellIndex4).setCellValue(entity.getComment());
            sheet4.getRow(rowIndex4).getCell(CellIndex4).setCellStyle(style);
        }
        //АРЕНДОВАТЬ ПОМЕЩЕНИЕ В ПРОМПАРКЕ(ПОЛУЧИТЬ ЗАЯВКУ) OPERATORS ID=4
        Sheet sheet6                     = wb.getSheetAt(5);
        int rowIndex6    = 0;
        int CellIndex6   = 0;
        sheets6.createRow(rowIndex6) .createCell(CellIndex6)  .setCellValue("№");
        sheet6   .getRow(rowIndex6)    .getCell(CellIndex6)     .setCellStyle(styleTitle);
        sheets6  .getRow(rowIndex6)    .createCell(++CellIndex6).setCellValue("ФИО");
        sheet6   .getRow(rowIndex6)    .getCell(CellIndex6)     .setCellStyle(styleTitle);
        sheets6  .getRow(rowIndex6)    .createCell(++CellIndex6).setCellValue("Контакты");
        sheet6   .getRow(rowIndex6)    .getCell(CellIndex6)     .setCellStyle(styleTitle);
        sheets6  .getRow(rowIndex6)    .createCell(++CellIndex6).setCellValue("E-mail");
        sheet6   .getRow(rowIndex6)    .getCell(CellIndex6)     .setCellStyle(styleTitle);
        sheets6  .getRow(rowIndex6)    .createCell(++CellIndex6).setCellValue("Название компании");
        sheet6   .getRow(rowIndex6)    .getCell(CellIndex6)     .setCellStyle(styleTitle);
        sheets6  .getRow(rowIndex6)    .createCell(++CellIndex6).setCellValue("Отрасль вашей компании");
        sheet6   .getRow(rowIndex6)    .getCell(CellIndex6)     .setCellStyle(styleTitle);
        sheets6  .getRow(rowIndex6)    .createCell(++CellIndex6).setCellValue("Ваш вопрос для консультации");
        sheet6   .getRow(rowIndex6)    .getCell(CellIndex6)     .setCellStyle(styleTitle);
        sheets6  .getRow(rowIndex6)    .createCell(++CellIndex6).setCellValue("Поле для дополнительных комментариев");
        sheet6   .getRow(rowIndex6)    .getCell(CellIndex6)     .setCellStyle(styleTitle);

        for (Operators entity: allOperators4) {
            CellIndex6 = 0;
            sheets6.createRow(++rowIndex6).createCell(CellIndex6).setCellValue(entity.getId());
            sheet6.getRow(rowIndex6).getCell(CellIndex6).setCellStyle(style);
            sheets6.getRow(rowIndex6).createCell(++CellIndex6).setCellValue(entity.getFullName());
            sheet6.getRow(rowIndex6).getCell(CellIndex6).setCellStyle(style);
            sheets6.getRow(rowIndex6).createCell(++CellIndex6).setCellValue(entity.getPhoneNumber());
            sheet6.getRow(rowIndex6).getCell(CellIndex6).setCellStyle(style);
            sheets6.getRow(rowIndex6).createCell(++CellIndex6).setCellValue(entity.getEmail());
            sheet6.getRow(rowIndex6).getCell(CellIndex6).setCellStyle(style);
            sheets6.getRow(rowIndex6).createCell(++CellIndex6).setCellValue(entity.getCompany());
            sheet6.getRow(rowIndex6).getCell(CellIndex6).setCellStyle(style);
            sheets6.getRow(rowIndex6).createCell(++CellIndex6).setCellValue(entity.getDepartment());
            sheet6.getRow(rowIndex6).getCell(CellIndex6).setCellStyle(style);
            sheets6.getRow(rowIndex6).createCell(++CellIndex6).setCellValue(entity.getQuestion());
            sheet6.getRow(rowIndex6).getCell(CellIndex6).setCellStyle(style);
            sheets6.getRow(rowIndex6).createCell(++CellIndex6).setCellValue(entity.getComment());
            sheet6.getRow(rowIndex6).getCell(CellIndex6).setCellStyle(style);
        }

//        АРЕНДОВАТЬ ПОМЕЩЕНИЕ В ПРОМПАРКЕ(Получить консультацию) OPERATORS ID=4
        Sheet sheet7                     = wb.getSheetAt(6);
        int rowIndex7    = 0;
        int CellIndex7   = 0;
        sheets7.createRow(rowIndex7) .createCell(CellIndex7)  .setCellValue("№");
        sheet7   .getRow(rowIndex7)    .getCell(CellIndex7)     .setCellStyle(styleTitle);
        sheets7.getRow(rowIndex7)    .createCell(++CellIndex7).setCellValue("ФИО");
        sheet7   .getRow(rowIndex7)    .getCell(CellIndex7)     .setCellStyle(styleTitle);
        sheets7.getRow(rowIndex7)    .createCell(++CellIndex7).setCellValue("Контакты");
        sheet7   .getRow(rowIndex7)    .getCell(CellIndex7)     .setCellStyle(styleTitle);
        sheets7.getRow(rowIndex7)    .createCell(++CellIndex7).setCellValue("E-mail");
        sheet7   .getRow(rowIndex7)    .getCell(CellIndex7)     .setCellStyle(styleTitle);
        sheets7.getRow(rowIndex7)    .createCell(++CellIndex7).setCellValue("Название компании");
        sheet7   .getRow(rowIndex7)    .getCell(CellIndex7)     .setCellStyle(styleTitle);
        sheets7.getRow(rowIndex7)    .createCell(++CellIndex7).setCellValue("Отрасль вашей компании");
        sheet7   .getRow(rowIndex7)    .getCell(CellIndex7)     .setCellStyle(styleTitle);
        sheets7.getRow(rowIndex7)    .createCell(++CellIndex7).setCellValue("Интересующая площадь");
        sheet7   .getRow(rowIndex7)    .getCell(CellIndex7)     .setCellStyle(styleTitle);
        sheets7.getRow(rowIndex7)    .createCell(++CellIndex7).setCellValue("Поле для дополнительных комментариев");
        sheet7   .getRow(rowIndex7)    .getCell(CellIndex7)     .setCellStyle(styleTitle);

        for (Application entity: allApplication5) {
            CellIndex7 = 0;
            sheets7.createRow(++rowIndex7).createCell(CellIndex7).setCellValue(entity.getId());
            sheet7.getRow(rowIndex7).getCell(CellIndex7).setCellStyle(style);
            sheets7.getRow(rowIndex7).createCell(++CellIndex7).setCellValue(entity.getFullName());
            sheet7.getRow(rowIndex7).getCell(CellIndex7).setCellStyle(style);
            sheets7.getRow(rowIndex7).createCell(++CellIndex7).setCellValue(entity.getPhoneNumber());
            sheet7.getRow(rowIndex7).getCell(CellIndex7).setCellStyle(style);
            sheets7.getRow(rowIndex7).createCell(++CellIndex7).setCellValue(entity.getEmail());
            sheet7.getRow(rowIndex7).getCell(CellIndex7).setCellStyle(style);
            sheets7.getRow(rowIndex7).createCell(++CellIndex7).setCellValue(entity.getCompany());
            sheet7.getRow(rowIndex7).getCell(CellIndex7).setCellStyle(style);
            sheets7.getRow(rowIndex7).createCell(++CellIndex7).setCellValue(entity.getDepartment());
            sheet7.getRow(rowIndex7).getCell(CellIndex7).setCellStyle(style);
            sheets7.getRow(rowIndex7).createCell(++CellIndex7).setCellValue(entity.getRequest());
            sheet7.getRow(rowIndex7).getCell(CellIndex7).setCellStyle(style);
            sheets7.getRow(rowIndex7).createCell(++CellIndex7).setCellValue(entity.getComment());
            sheet7.getRow(rowIndex7).getCell(CellIndex7).setCellStyle(style);
        }

        //АРЕНДОВАТЬ АКТИВЫ СПК(ПОДАТЬ ЗАЯВКУ(ПОДАТЬ ЗАЯВКУ)) APPLICATION ID=1
        Sheet sheet8                     = wb.getSheetAt(7);
        int rowIndex8    = 0;
        int CellIndex8   = 0;
        sheets8.createRow(rowIndex8) .createCell(CellIndex8)  .setCellValue("№");
        sheet8   .getRow(rowIndex8)    .getCell(CellIndex8)     .setCellStyle(styleTitle);
        sheets8  .getRow(rowIndex8)    .createCell(++CellIndex8).setCellValue("ФИО");
        sheet8   .getRow(rowIndex8)    .getCell(CellIndex8)     .setCellStyle(styleTitle);
        sheets8  .getRow(rowIndex8)    .createCell(++CellIndex8).setCellValue("Контакты");
        sheet8   .getRow(rowIndex8)    .getCell(CellIndex8)     .setCellStyle(styleTitle);
        sheets8  .getRow(rowIndex8)    .createCell(++CellIndex8).setCellValue("E-mail");
        sheet8   .getRow(rowIndex8)    .getCell(CellIndex8)     .setCellStyle(styleTitle);
        sheets8  .getRow(rowIndex8)    .createCell(++CellIndex8).setCellValue("Название компании");
        sheet8   .getRow(rowIndex8)    .getCell(CellIndex8)     .setCellStyle(styleTitle);
        sheets8  .getRow(rowIndex8)    .createCell(++CellIndex8).setCellValue("Отрасль вашей компании");
        sheet8   .getRow(rowIndex8)    .getCell(CellIndex8)     .setCellStyle(styleTitle);
        sheets8  .getRow(rowIndex8)    .createCell(++CellIndex8).setCellValue("Какой объект вас заинтересовал");
        sheet8   .getRow(rowIndex8)    .getCell(CellIndex8)     .setCellStyle(styleTitle);
        sheets8  .getRow(rowIndex8)    .createCell(++CellIndex8).setCellValue("Поле для дополнительных комментариев");
        sheet8   .getRow(rowIndex8)    .getCell(CellIndex8)     .setCellStyle(styleTitle);

        for (Application entity: allApplication1) {
            CellIndex8 = 0;
            sheets8.createRow(++rowIndex8).createCell(CellIndex8).setCellValue(entity.getId());
            sheet8.getRow(rowIndex8).getCell(CellIndex8).setCellStyle(style);
            sheets8.getRow(rowIndex8).createCell(++CellIndex8).setCellValue(entity.getFullName());
            sheet8.getRow(rowIndex8).getCell(CellIndex8).setCellStyle(style);
            sheets8.getRow(rowIndex8).createCell(++CellIndex8).setCellValue(entity.getPhoneNumber());
            sheet8.getRow(rowIndex8).getCell(CellIndex8).setCellStyle(style);
            sheets8.getRow(rowIndex8).createCell(++CellIndex8).setCellValue(entity.getEmail());
            sheet8.getRow(rowIndex8).getCell(CellIndex8).setCellStyle(style);
            sheets8.getRow(rowIndex8).createCell(++CellIndex8).setCellValue(entity.getCompany());
            sheet8.getRow(rowIndex8).getCell(CellIndex8).setCellStyle(style);
            sheets8.getRow(rowIndex8).createCell(++CellIndex8).setCellValue(entity.getDepartment());
            sheet8.getRow(rowIndex8).getCell(CellIndex8).setCellStyle(style);
            sheets8.getRow(rowIndex8).createCell(++CellIndex8).setCellValue(entity.getRequest());
            sheet8.getRow(rowIndex8).getCell(CellIndex8).setCellStyle(style);
            sheets8.getRow(rowIndex8).createCell(++CellIndex8).setCellValue(entity.getComment());
            sheet8.getRow(rowIndex8).getCell(CellIndex8).setCellStyle(style);
        }

        //АРЕНДОВАТЬ АКТИВЫ СПК(ПОЛУЧИТЬ КОНСУЛЬТАЦИЮ) OPERATORS ID=1
        Sheet sheet9                     = wb.getSheetAt(8);
        int rowIndex9    = 0;
        int CellIndex9   = 0;
        sheets9.createRow(rowIndex9) .createCell(CellIndex9)  .setCellValue("№");
        sheet9   .getRow(rowIndex9)    .getCell(CellIndex9)     .setCellStyle(styleTitle);
        sheets9  .getRow(rowIndex9)    .createCell(++CellIndex9).setCellValue("ФИО");
        sheet9   .getRow(rowIndex9)    .getCell(CellIndex9)     .setCellStyle(styleTitle);
        sheets9  .getRow(rowIndex9)    .createCell(++CellIndex9).setCellValue("Контакты");
        sheet9   .getRow(rowIndex9)    .getCell(CellIndex9)     .setCellStyle(styleTitle);
        sheets9  .getRow(rowIndex9)    .createCell(++CellIndex9).setCellValue("E-mail");
        sheet9   .getRow(rowIndex9)    .getCell(CellIndex9)     .setCellStyle(styleTitle);
        sheets9  .getRow(rowIndex9)    .createCell(++CellIndex9).setCellValue("Название компании");
        sheet9   .getRow(rowIndex9)    .getCell(CellIndex9)     .setCellStyle(styleTitle);
        sheets9  .getRow(rowIndex9)    .createCell(++CellIndex9).setCellValue("Отрасль вашей компании");
        sheet9   .getRow(rowIndex9)    .getCell(CellIndex9)     .setCellStyle(styleTitle);
        sheets9  .getRow(rowIndex9)    .createCell(++CellIndex9).setCellValue("Ваш вопрос для консультации");
        sheet9   .getRow(rowIndex9)    .getCell(CellIndex9)     .setCellStyle(styleTitle);
        sheets9  .getRow(rowIndex9)    .createCell(++CellIndex9).setCellValue("Поле для дополнительных комментариев");
        sheet9   .getRow(rowIndex9)    .getCell(CellIndex9)     .setCellStyle(styleTitle);

        for (Operators entity: allOperators1) {
            CellIndex9 = 0;
            sheets9.createRow(++rowIndex9).createCell(CellIndex9).setCellValue(entity.getId());
            sheet9.getRow(rowIndex9).getCell(CellIndex9).setCellStyle(style);
            sheets9.getRow(rowIndex9).createCell(++CellIndex9).setCellValue(entity.getFullName());
            sheet9.getRow(rowIndex9).getCell(CellIndex9).setCellStyle(style);
            sheets9.getRow(rowIndex9).createCell(++CellIndex9).setCellValue(entity.getPhoneNumber());
            sheet9.getRow(rowIndex9).getCell(CellIndex9).setCellStyle(style);
            sheets9.getRow(rowIndex9).createCell(++CellIndex9).setCellValue(entity.getEmail());
            sheet9.getRow(rowIndex9).getCell(CellIndex9).setCellStyle(style);
            sheets9.getRow(rowIndex9).createCell(++CellIndex9).setCellValue(entity.getCompany());
            sheet9.getRow(rowIndex9).getCell(CellIndex9).setCellStyle(style);
            sheets9.getRow(rowIndex9).createCell(++CellIndex9).setCellValue(entity.getDepartment());
            sheet9.getRow(rowIndex9).getCell(CellIndex9).setCellStyle(style);
            sheets9.getRow(rowIndex9).createCell(++CellIndex9).setCellValue(entity.getQuestion());
            sheet9.getRow(rowIndex9).getCell(CellIndex9).setCellStyle(style);
            sheets9.getRow(rowIndex9).createCell(++CellIndex9).setCellValue(entity.getComment());
            sheet9.getRow(rowIndex9).getCell(CellIndex9).setCellStyle(style);
        }

        //ДОЧЕРНИЕ КОМПАНИИ ОТ 500ТЫС ДО 55МЛН APPLICATION ID=3
        Sheet sheet10                     = wb.getSheetAt(9);
        int rowIndex10    = 0;
        int CellIndex10   = 0;
        sheets10.createRow(rowIndex10) .createCell(CellIndex10)  .setCellValue("№");
        sheet10   .getRow(rowIndex10)    .getCell(CellIndex10)     .setCellStyle(styleTitle);
        sheets10.getRow(rowIndex10)    .createCell(++CellIndex10).setCellValue("ФИО");
        sheet10   .getRow(rowIndex10)    .getCell(CellIndex10)     .setCellStyle(styleTitle);
        sheets10.getRow(rowIndex10)    .createCell(++CellIndex10).setCellValue("Контакты");
        sheet10   .getRow(rowIndex10)    .getCell(CellIndex10)     .setCellStyle(styleTitle);
        sheets10.getRow(rowIndex10)    .createCell(++CellIndex10).setCellValue("E-mail");
        sheet10   .getRow(rowIndex10)    .getCell(CellIndex10)     .setCellStyle(styleTitle);
        sheets10.getRow(rowIndex10)    .createCell(++CellIndex10).setCellValue("Название компании");
        sheet10   .getRow(rowIndex10)    .getCell(CellIndex10)     .setCellStyle(styleTitle);
        sheets10.getRow(rowIndex10)    .createCell(++CellIndex10).setCellValue("Отрасль вашей компании");
        sheet10   .getRow(rowIndex10)    .getCell(CellIndex10)     .setCellStyle(styleTitle);
        sheets10.getRow(rowIndex10)    .createCell(++CellIndex10).setCellValue("Интересующая сумм11");
        sheet10   .getRow(rowIndex10)    .getCell(CellIndex10)     .setCellStyle(styleTitle);
        sheets10.getRow(rowIndex10)    .createCell(++CellIndex10).setCellValue("Поле для дополнительных комментариев");
        sheet10   .getRow(rowIndex10)    .getCell(CellIndex10)     .setCellStyle(styleTitle);

        for (Application entity: allApplication3) {
            CellIndex10 = 0;
            sheets10.createRow(++rowIndex10).createCell(CellIndex10).setCellValue(entity.getId());
            sheet10.getRow(rowIndex10).getCell(CellIndex10).setCellStyle(style);
            sheets10.getRow(rowIndex10).createCell(++CellIndex10).setCellValue(entity.getFullName());
            sheet10.getRow(rowIndex10).getCell(CellIndex10).setCellStyle(style);
            sheets10.getRow(rowIndex10).createCell(++CellIndex10).setCellValue(entity.getPhoneNumber());
            sheet10.getRow(rowIndex10).getCell(CellIndex10).setCellStyle(style);
            sheets10.getRow(rowIndex10).createCell(++CellIndex10).setCellValue(entity.getEmail());
            sheet10.getRow(rowIndex10).getCell(CellIndex10).setCellStyle(style);
            sheets10.getRow(rowIndex10).createCell(++CellIndex10).setCellValue(entity.getCompany());
            sheet10.getRow(rowIndex10).getCell(CellIndex10).setCellStyle(style);
            sheets10.getRow(rowIndex10).createCell(++CellIndex10).setCellValue(entity.getDepartment());
            sheet10.getRow(rowIndex10).getCell(CellIndex10).setCellStyle(style);
            sheets10.getRow(rowIndex10).createCell(++CellIndex10).setCellValue(entity.getRequest());
            sheet10.getRow(rowIndex10).getCell(CellIndex10).setCellStyle(style);
            sheets10.getRow(rowIndex10).createCell(++CellIndex10).setCellValue(entity.getComment());
            sheet10.getRow(rowIndex10).getCell(CellIndex10).setCellStyle(style);
        }

        //ДОЧЕРНИЕ КОМПАНИИ ОТ 500ТЫС ДО 55МЛН APPLICATION ID=4
        Sheet sheet11                     = wb.getSheetAt(10);
        int rowIndex11    = 0;
        int CellIndex11   = 0;
        sheets11.createRow(rowIndex11) .createCell(CellIndex11)  .setCellValue("№");
        sheet11   .getRow(rowIndex11)    .getCell(CellIndex11)     .setCellStyle(styleTitle);
        sheets11.getRow(rowIndex11)    .createCell(++CellIndex11).setCellValue("ФИО");
        sheet11   .getRow(rowIndex11)    .getCell(CellIndex11)     .setCellStyle(styleTitle);
        sheets11.getRow(rowIndex11)    .createCell(++CellIndex11).setCellValue("Контакты");
        sheet11   .getRow(rowIndex11)    .getCell(CellIndex11)     .setCellStyle(styleTitle);
        sheets11.getRow(rowIndex11)    .createCell(++CellIndex11).setCellValue("E-mail");
        sheet11   .getRow(rowIndex11)    .getCell(CellIndex11)     .setCellStyle(styleTitle);
        sheets11.getRow(rowIndex11)    .createCell(++CellIndex11).setCellValue("Название компании");
        sheet11   .getRow(rowIndex11)    .getCell(CellIndex11)     .setCellStyle(styleTitle);
        sheets11.getRow(rowIndex11)    .createCell(++CellIndex11).setCellValue("Отрасль вашей компании");
        sheet11   .getRow(rowIndex11)    .getCell(CellIndex11)     .setCellStyle(styleTitle);
        sheets11.getRow(rowIndex11)    .createCell(++CellIndex11).setCellValue("Интересующая сумма");
        sheet11   .getRow(rowIndex11)    .getCell(CellIndex11)     .setCellStyle(styleTitle);
        sheets11.getRow(rowIndex11)    .createCell(++CellIndex11).setCellValue("Поле для дополнительных комментариев");
        sheet11   .getRow(rowIndex11)    .getCell(CellIndex11)     .setCellStyle(styleTitle);

        for (Application entity: allApplication4) {
            CellIndex11 = 0;
            sheets11.createRow(++rowIndex11).createCell(CellIndex11).setCellValue(entity.getId());
            sheet11.getRow(rowIndex11).getCell(CellIndex11).setCellStyle(style);
            sheets11.getRow(rowIndex11).createCell(++CellIndex11).setCellValue(entity.getFullName());
            sheet11.getRow(rowIndex11).getCell(CellIndex11).setCellStyle(style);
            sheets11.getRow(rowIndex11).createCell(++CellIndex11).setCellValue(entity.getPhoneNumber());
            sheet11.getRow(rowIndex11).getCell(CellIndex11).setCellStyle(style);
            sheets11.getRow(rowIndex11).createCell(++CellIndex11).setCellValue(entity.getEmail());
            sheet11.getRow(rowIndex11).getCell(CellIndex11).setCellStyle(style);
            sheets11.getRow(rowIndex11).createCell(++CellIndex11).setCellValue(entity.getCompany());
            sheet11.getRow(rowIndex11).getCell(CellIndex11).setCellStyle(style);
            sheets11.getRow(rowIndex11).createCell(++CellIndex11).setCellValue(entity.getDepartment());
            sheet11.getRow(rowIndex11).getCell(CellIndex11).setCellStyle(style);
            sheets11.getRow(rowIndex11).createCell(++CellIndex11).setCellValue(entity.getRequest());
            sheet11.getRow(rowIndex11).getCell(CellIndex11).setCellStyle(style);
            sheets11.getRow(rowIndex11).createCell(++CellIndex11).setCellValue(entity.getComment());
            sheet11.getRow(rowIndex11).getCell(CellIndex11).setCellStyle(style);
        }

        //ЧАСТО ЗАДАВАЕМЫЕ ВОПРОСЫ
        Sheet sheet12                     = wb.getSheetAt(11);
        int rowIndex12    = 0;
        int CellIndex12   = 0;
        sheets12  .createRow(rowIndex12) .createCell(CellIndex12)  .setCellValue("№");
        sheet12   .getRow(rowIndex12)    .getCell(CellIndex12)     .setCellStyle(styleTitle);
        sheets12  .getRow(rowIndex12)    .createCell(++CellIndex12).setCellValue("ФИО");
        sheet12   .getRow(rowIndex12)    .getCell(CellIndex12)     .setCellStyle(styleTitle);
        sheets12  .getRow(rowIndex12)    .createCell(++CellIndex12).setCellValue("Контакты");
        sheet12   .getRow(rowIndex12)    .getCell(CellIndex12)     .setCellStyle(styleTitle);
        sheets12  .getRow(rowIndex12)    .createCell(++CellIndex12).setCellValue("E-mail");
        sheet12   .getRow(rowIndex12)    .getCell(CellIndex12)     .setCellStyle(styleTitle);
        sheets12  .getRow(rowIndex12)    .createCell(++CellIndex12).setCellValue("Название компании");
        sheet12   .getRow(rowIndex12)    .getCell(CellIndex12)     .setCellStyle(styleTitle);
        sheets12  .getRow(rowIndex12)    .createCell(++CellIndex12).setCellValue("Отрасль вашей компании");
        sheet12   .getRow(rowIndex12)    .getCell(CellIndex12)     .setCellStyle(styleTitle);
        sheets12  .getRow(rowIndex12)    .createCell(++CellIndex12).setCellValue("Ваш вопрос для консультации");
        sheet12   .getRow(rowIndex12)    .getCell(CellIndex12)     .setCellStyle(styleTitle);

        for (Question entity: allQuestion) {
            CellIndex12 = 0;
            sheets12.createRow(++rowIndex12).createCell(CellIndex12).setCellValue(entity.getId());
            sheet12.getRow(rowIndex12).getCell(CellIndex12).setCellStyle(style);
            sheets12.getRow(rowIndex12).createCell(++CellIndex12).setCellValue(entity.getFullName());
            sheet12.getRow(rowIndex12).getCell(CellIndex12).setCellStyle(style);
            sheets12.getRow(rowIndex12).createCell(++CellIndex12).setCellValue(entity.getContact());
            sheet12.getRow(rowIndex12).getCell(CellIndex12).setCellStyle(style);
            sheets12.getRow(rowIndex12).createCell(++CellIndex12).setCellValue(entity.getEmail());
            sheet12.getRow(rowIndex12).getCell(CellIndex12).setCellStyle(style);
            sheets12.getRow(rowIndex12).createCell(++CellIndex12).setCellValue(entity.getCompany());
            sheet12.getRow(rowIndex12).getCell(CellIndex12).setCellStyle(style);
            sheets12.getRow(rowIndex12).createCell(++CellIndex12).setCellValue(entity.getDepartment());
            sheet12.getRow(rowIndex12).getCell(CellIndex12).setCellStyle(style);
            sheets12.getRow(rowIndex12).createCell(++CellIndex12).setCellValue(entity.getQuestion());
            sheet12.getRow(rowIndex12).getCell(CellIndex12).setCellStyle(style);
        }


        String[] splitWidth = "10000;10000;10000;10000;10000;10000;10000;10000".split(";");
        for (int i = 0; i < splitWidth.length; i++) {
            if (splitWidth[i].equalsIgnoreCase("auto")) {
                sheets       .autoSizeColumn(i);
                sheets1.autoSizeColumn(i);
                sheets2.autoSizeColumn(i);
                sheets3.autoSizeColumn(i);
                sheets5.autoSizeColumn(i);
                sheets6.autoSizeColumn(i);
                sheets7.autoSizeColumn(i);
                sheets8.autoSizeColumn(i);
                sheets9.autoSizeColumn(i);
                sheets10.autoSizeColumn(i);
                sheets11.autoSizeColumn(i);
                sheets12.autoSizeColumn(i);
            } else {
                int size = 0;
                try {
                    size = Integer.parseInt(splitWidth[i].replaceAll("[^0-9]", ""));
                } catch (NumberFormatException e) {
                    log.warn("Error in message № 309 - {}", splitWidth[i]);
                }
                if (size > 0) {
                    sheets.setColumnWidth(i, size);
                    sheets1.setColumnWidth(i,size);
                    sheets2.setColumnWidth(i,size);
                    sheets3.setColumnWidth(i,size);
                    sheets5.setColumnWidth(i,size);
                    sheets6.setColumnWidth(i,size);
                    sheets7.setColumnWidth(i,size);
                    sheets8.setColumnWidth(i,size);
                    sheets9.setColumnWidth(i,size);
                    sheets10.setColumnWidth(i,size);
                    sheets11.setColumnWidth(i,size);
                    sheets12.setColumnWidth(i,size);
                }
            }
        }
        String filename = String.format("Отчеты %s.xlsx", DateUtil.getDayDate(new Date()));
        deleteMessage(messagePreviewReport);
        bot.execute(new SendDocument().setChatId(chatId).setDocument(filename, getInputStream(wb)));
    }

    private InputStream getInputStream(XSSFWorkbook workbook) {
        ByteArrayOutputStream tables = new ByteArrayOutputStream();
        try {
            workbook.write(tables);
        } catch (IOException e) {
            log.error("Can't write table to wb, case: {}", e);
        }
        return new ByteArrayInputStream(tables.toByteArray());
    }
}

