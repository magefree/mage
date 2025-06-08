package mage.verify.mtgjson;

import java.util.List;

/**
 * Commanders Spellbook api: full combo
 * <p>
 * API docs <a href="https://backend.commanderspellbook.com/variants">here</a>
 *
 * @author JayDi85
 */
public final class SpellBookCombo {
    public String id; // combo id
    public List<SpellBookComboPart> uses; // combo parts
    public String bracketTag; // can be useful
    public String description;
}
