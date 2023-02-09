package ru.surin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public enum ChatMemberStatus {
    ADMINISTRATOR("administrator"),
    BANNED("kicked"),
    LEFT("left"),
    MEMBER("member"),
    OWNER("creator"),
    RESTRICTED("restricted");


    private String status;

    public static ChatMemberStatus fromString(String status) {
        for (ChatMemberStatus chatMemberStatus : ChatMemberStatus.values()) {
            if (chatMemberStatus.status.equals(status)) {
                return chatMemberStatus;
            }
        }
        return null;
    }

    public static List<ChatMemberStatus> getIsCurrentMembersList() {
        return List.of(ADMINISTRATOR, MEMBER, OWNER);
    }

}