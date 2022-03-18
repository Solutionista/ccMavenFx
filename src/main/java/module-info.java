module de.sterzsolutions.ccmavenfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires jbcrypt;

    requires xchange.core;
    requires xchange.binance;
    requires xchange.stream.binance;
    requires xchange.stream.core;
    requires xchange.stream.service.core;
    requires xchange.stream.service.netty;


    opens de.sterzsolutions.ccmavenfx to javafx.controls, javafx.fxml;
    exports de.sterzsolutions.ccmavenfx;
}