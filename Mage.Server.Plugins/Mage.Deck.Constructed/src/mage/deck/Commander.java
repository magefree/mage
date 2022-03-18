package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.DeckValidatorErrorType;

import java.util.Map;

/**
 * @author Plopman
 */
public class Commander extends AbstractCommander {

    public Commander() {
        this("Commander");
        banned.add("Ancestral Recall");
        banned.add("Balance");
        banned.add("Biorhythm");
        banned.add("Black Lotus");
        banned.add("Braids, Cabal Minion");
        banned.add("Channel");
        banned.add("Cleanse");
        banned.add("Coalition Victory");
        banned.add("Crusade");
        banned.add("Emrakul, the Aeons Torn");
        banned.add("Erayo, Soratami Ascendant");
        banned.add("Fastbond");
        banned.add("Flash");
        banned.add("Gifts Ungiven");
        banned.add("Golos, Tireless Pilgrim");
        banned.add("Griselbrand");
        banned.add("Hullbreacher");
        banned.add("Imprison");
        banned.add("Invoke Prejudice");
        banned.add("Iona, Shield of Emeria");
        banned.add("Jihad");
        banned.add("Karakas");
        banned.add("Leovold, Emissary of Trest");
        banned.add("Library of Alexandria");
        banned.add("Limited Resources");
        banned.add("Lutri, the Spellchaser");
        banned.add("Mox Emerald");
        banned.add("Mox Jet");
        banned.add("Mox Pearl");
        banned.add("Mox Ruby");
        banned.add("Mox Sapphire");
        banned.add("Panoptic Mirror");
        banned.add("Paradox Engine");
        banned.add("Pradesh Gypsies");
        banned.add("Primeval Titan");
        banned.add("Prophet of Kruphix");
        banned.add("Recurring Nightmare");
        banned.add("Rofellos, Llanowar Emissary");
        banned.add("Stone-Throwing Devils");
        banned.add("Sundering Titan");
        banned.add("Sway of the Stars");
        banned.add("Sylvan Primordial");
        banned.add("Time Vault");
        banned.add("Time Walk");
        banned.add("Tinker");
        banned.add("Tolarian Academy");
        banned.add("Trade Secrets");
        banned.add("Upheaval");
        banned.add("Yawgmoth's Bargain");
    }

    protected Commander(String name) {
        super(name);
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isEternalLegal()) {
                setCodes.add(set.getCode());
            }
        }
    }

    @Override
    protected boolean checkBanned(Map<String, Integer> counts) {
        boolean valid = true;
        for (String bannedCard : banned) {
            if (counts.containsKey(bannedCard)) {
                addError(DeckValidatorErrorType.BANNED, bannedCard, "Banned", true);
                valid = false;
            }
        }
        return valid;
    }
}
