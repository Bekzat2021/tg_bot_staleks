package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi=new TelegramBotsApi(DefaultBotSession.class);
        //C:\Users\Bekzat\Desktop\java_for_work\test_bot\test_bot\src\main\java\org\example\config.properties
        Properties prop=new Properties();
        try{
            FileInputStream propFile=new FileInputStream("src/main/resources/config.properties");
            prop.load(propFile);;
            String botName=prop.getProperty("bot.name");
            String botToken=prop.getProperty("bot.token");
            botsApi.registerBot(new Bot(botName, botToken));
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }

        db_connection.selectAll();
    }
}