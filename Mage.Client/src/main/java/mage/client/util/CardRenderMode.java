package mage.client.util;

import java.util.ArrayList;
import java.util.List;

public enum CardRenderMode {

    MTGO("MTGO", 0),
    IMAGE("Image", 1),
    FORCED_M15("Forced M15", 2),
    FORCED_RETRO("Forced Retro", 3);

    private final String text;
    private final int id;

    CardRenderMode(String text, int id) {
        this.text = text;
        this.id = id;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public static String[] toList() {
        List<String> list = new ArrayList<>();
        for (CardRenderMode mode : CardRenderMode.values()) {
            list.add(mode.toString());
        }
        return list.toArray(new String[0]);
    }

    public static CardRenderMode fromId(int id) {
        for (CardRenderMode mode : CardRenderMode.values()) {
            if (mode.getId() == id) {
                return mode;
            }
        }
        return MTGO;
    }

    public static CardRenderMode fromString(String text) {
        for (CardRenderMode mode : CardRenderMode.values()) {
            if (mode.text.equals(text)) {
                return mode;
            }
        }
        return MTGO;
    }
}
