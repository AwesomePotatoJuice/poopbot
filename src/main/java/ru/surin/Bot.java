package ru.surin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.vdurmont.emoji.EmojiParser;
import lombok.Setter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static ru.surin.ChatMemberStatus.getIsCurrentMembersList;

@Setter
public class Bot extends TelegramLongPollingBot {

    public static final String FILE_NAME = "debug.txt";

    private HashSet<Long> chats = new HashSet<>();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String text = update.getMessage().getText();
            switch (text.split("@")[0]) {
                case "/start":
                    startCommand(update);
                    break;
                case "/poopHere":
                    poopHereCommand(update);
                    break;
                case "/stop":
                    chats.remove(update.getMessage().getChatId());
                    break;
                case "/pooped":
                    poopedCommand(update.getMessage().getFrom());
                    break;
            }
        }
    }

    private void poopHereCommand(Update update) {
        chats.add(update.getMessage().getChatId());
        try {
            saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveToFile() throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        Gson gson = gsonBuilder.create();
        FileWriter writer = new FileWriter(FILE_NAME);
        gson.toJson(chats, writer);
        writer.flush();
    }

    private void poopedCommand(User from) {
        for (Long chat : chats) {

            if (!isMember(from, chat)){
                continue;
            }

            SendMessage message = new SendMessage();
            message.setChatId(chat.toString());
            message.setText(EmojiParser.parseToUnicode("Someone pooped! :poop: :upside_down: :poop:"));
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isMember(User from, Long chatId) {
        try {
            GetChatMember chat = GetChatMember.builder()
                    .chatId(chatId)
                    .userId(from.getId())
                    .build();
            ChatMember execute = execute(chat);
            return getIsCurrentMembersList().contains(ChatMemberStatus.fromString(execute.getStatus()));
        } catch (Exception e) {
            return false;
        }
    }

    private void startCommand(Update update) {
        SendMessage messageStart = new SendMessage();
        messageStart.setChatId(update.getMessage().getChatId().toString());
        messageStart.enableMarkdown(true);
        messageStart.setReplyMarkup(getSettingsKeyboard());
        messageStart.setText("Here is your keyboard");
        try {
            execute(messageStart);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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
