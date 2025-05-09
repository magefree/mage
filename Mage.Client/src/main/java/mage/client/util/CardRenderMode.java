package mage.client.util;

import java.util.ArrayList;
import java.util.List;

public enum CardRenderMode {

    MTGO("MTGO"),
    IMAGE("Image"),
    FORCED_M15("Forced M15"),
    FORCED_RETRO("Forced Retro");

    private final String text;

    CardRenderMode(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getText() {
        return text;
    }

    public static String[] toList() {
        List<String> list = new ArrayList<>();
        for (CardRenderMode mode : CardRenderMode.values()) {
            list.add(mode.toString());
        }
        return list.toArray(new String[0]);
    }

    public static CardRenderMode fromString(String text) {
        for (CardRenderMode mode : CardRenderMode.values()) {
            if (mode.text.equals(text)) {
                return mode;
            }
        }
        return IMAGE;
    }
}
