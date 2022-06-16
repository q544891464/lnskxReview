package org.opsli.api.wrapper.system.apply;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class PrizeConverter implements Converter<String> {
    @Override
    public Class supportJavaTypeKey() {
        return null;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return null;
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return null;
    }

    @Override
    public CellData convertToExcelData(String s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {

        switch (s) {
            case "1":
                return new CellData("一等奖");
            case "2":
                return new CellData("二等奖");
            case "3":
                return new CellData("三等奖");
            case "0":
                return new CellData("未获奖");
            default:
                return new CellData("未获奖");
        }
    }
}
