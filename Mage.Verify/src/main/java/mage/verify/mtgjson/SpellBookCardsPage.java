package mage.verify.mtgjson;

import java.util.List;

/**
 * Commanders Spellbook api: page class
 * <p>
 * API docs <a href="https://backend.commanderspellbook.com/variants">here</a>
 *
 * @author JayDi85
 */
public final class SpellBookCardsPage {
    public int count;
    public String next; // can be null on last page
    public String previous;
    public List<SpellBookCombo> results; // empty on too big page
}
