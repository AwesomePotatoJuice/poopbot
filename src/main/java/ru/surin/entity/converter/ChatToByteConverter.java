package ru.surin.entity.converter;

import ru.surin.entity.ChatEntity;

public class ChatToByteConverter extends AbstractObjectToByteConverter<ChatEntity>{

    public ChatToByteConverter() {
        super(ChatEntity.class);
    }
}