package ru.surin.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FileHelper {

    public static Set<Long> readLongsFromFile(String fileName) throws FileNotFoundException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        Gson gson = gsonBuilder.create();
        HashSet<String> hashSet = gson.fromJson(new FileReader(fileName), HashSet.class);
        return hashSet != null ?
                hashSet.stream().map(Long::parseLong).collect(Collectors.toCollection(HashSet::new)) :
                new HashSet<>();
    }
}
