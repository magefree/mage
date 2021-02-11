package mage.constants;

public enum MultiAmountType {
    MANA("Add mana", "Distribute mana among colors"),
    DAMAGE("Assign damage", "Assign damage among targets");

    private final String title;
    private final String header;

    MultiAmountType(String title, String header) {
        this.title = title;
        this.header = header;
    }

    public String getTitle() {
        return title;
    }

    public String getHeader() {
        return header;
    }
}
