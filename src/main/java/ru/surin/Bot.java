package ru.surin;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    HashSet<Long> chats = new HashSet<>();

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {

            String text = update.getMessage().getText();
            switch (text.split("@")[0]) {
                case "/start":
                    SendMessage messageStart = new SendMessage();
                    messageStart.setChatId(update.getMessage().getChatId().toString());
                    messageStart.enableMarkdown(true);
                    messageStart.setReplyMarkup(getSettingsKeyboard());
                    messageStart.setText("Here is your keyboard");
                    try {
                        execute(messageStart);
                    } catch (TelegramApiException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "/poopHere":
                    chats.add(update.getMessage().getChatId());
                    break;
                case "/stop":
                    chats.remove(update.getMessage().getChatId());
                    break;
                case "/pooped":
                    for (Long chat : chats) {
                        SendMessage message = new SendMessage();
                        message.setChatId(chat.toString());
                        message.setText(EmojiParser.parseToUnicode("Someone pooped! :poop: :upside_down: :poop:"));
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
            }
        }
    }



    @Override
    public String getBotUsername() {
        return "poopnotification_bot";
    }

    @Override
    public String getBotToken() {
        return "6050726252:AAEaQyw91ZHVgyTHR-NQwKOfCgnwouIniAw";
    }
    private static ReplyKeyboardMarkup getSettingsKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("/pooped");
        keyboardFirstRow.add("/pooped-how");
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }
}
