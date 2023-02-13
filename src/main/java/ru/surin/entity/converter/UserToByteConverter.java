package ru.surin.entity.converter;

import ru.surin.entity.UserEntity;

public class UserToByteConverter extends AbstractObjectToByteConverter<UserEntity>{

    public UserToByteConverter() {
        super(UserEntity.class);
    }
}