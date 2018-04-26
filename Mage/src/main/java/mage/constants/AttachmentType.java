package mage.constants;

/**
 *
 * @author North
 */
public enum AttachmentType {
    EQUIPMENT("Equipped"),
    AURA("Enchanted");

    String verb;

    public String verb(){
        return verb;
    }

    AttachmentType(String verb){
        this.verb = verb;
    }
}
