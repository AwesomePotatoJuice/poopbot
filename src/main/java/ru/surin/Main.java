package ru.surin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.stream.Collectors;

import static ru.surin.Bot.FILE_NAME;


public class Main {
    public static void main(String[] args) {

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            Bot bot = new Bot();
            botsApi.registerBot(bot);
            HashSet<Long> chats = readChatsFromFile();
            bot.setChats(chats == null ? new HashSet<>() : chats);
        } catch (TelegramApiException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static HashSet<Long> readChatsFromFile() throws FileNotFoundException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLongSerializationPolicy( LongSerializationPolicy.STRING );
        Gson gson = gsonBuilder.create();
        HashSet<String> hashSet = gson.fromJson(new FileReader(FILE_NAME), HashSet.class);
        return hashSet.stream().map(Long::parseLong).collect(Collectors.toCollection(HashSet::new));
    }
}

