/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ilnur.BenderBot.Bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 *
 * @author ЭмирНурияКарим
 */
public class BenderBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getBotToken() {
        return "7030876343:AAE0s4pLgyoTcqXikVAPf6jlIZeBDiPwvlM";
    }
    
    @Override
    public String getBotUsername() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void sendText(Long who, String what) {
        SendMessage message = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();
        
    }
    
    //sendText(1234L, "Glory to the robots");
    
}
