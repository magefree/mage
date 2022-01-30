package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidatorErrorType;

import java.util.HashMap;
import java.util.Map;

/**
 * @brief European Highlander is a classic competitive singleton format formalized around 2003.
 * It is also known as 'German Highlander'.
 * In short: 20 pts starting life, 100 card singleton deck without sideboard.
 * No command zone, so companions can only be played in the main deck.
 * There is a ban list but no points system.
 * More info at http://highlandermagic.info
 * Banned list here http://highlandermagic.info/banned-list
 * Watch list here http://highlandermagic.info/watch-list
 * @author ddzn
 */
public class EuropeanHighlander extends Constructed {

    public EuropeanHighlander() {
        super("European Highlander", "EuroLander");
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isEternalLegal()) {
                setCodes.add(set.getCode());
            }
        }

        // Gold-bordered cards (WC97 etc) used to be allowed until FNM allowed European Highlander to be sanctioned
        // Since then, the tournament organizer must decide if the cards are allowed

        // The following ban list is decided by the council in cooperation with the community (last update: 15.11.2020)

        // Silver-bordered cards are banned

        // Conspiracy cards are banned
        banned.add("Adriana's Valor");
        banned.add("Advantageous Proclamation");
        banned.add("Assemble the Rank and Vile");
        banned.add("Backup Plan");
        banned.add("Brago's Favor");
	banned.add("Cleanse");
	banned.add("Crusade");
        banned.add("Double Stroke");
        banned.add("Echoing Boon");
        banned.add("Emissary's Ploy");
        banned.add("Hired Heist");
        banned.add("Hold the Perimeter");
        banned.add("Hymn of the Wilds");
        banned.add("Immediate Action");
	banned.add("Imprison");
        banned.add("Incendiary Dissent");
        banned.add("Iterative Analysis");
	banned.add("Invoke Prejudice");
	banned.add("Jihad");
        banned.add("Muzzio's Preparations");
        banned.add("Natural Unity");
        banned.add("Power Play");
	banned.add("Pradesh Gypsies");
        banned.add("Secrets of Paradise");
        banned.add("Secret Summoning");
        banned.add("Sentinel Dispatch");
        banned.add("Sovereign's Realm");
	banned.add("Stone-Throwing Devils");
        banned.add("Summoner's Bond");
        banned.add("Unexpected Potential");
        banned.add("Weight Advantage");
        banned.add("Worldknit");

        // Dexterity cards are bannee
        banned.add("Chaos Orb");
        banned.add("Falling Star");

        // Sub-game cards are banned
        banned.add("Shahrazad");

        // Ante cards are banned
        banned.add("Amulet of Quoz");
        banned.add("Bronze Tablet");
        banned.add("Contract from Below");
        banned.add("Darkpact");
        banned.add("Demonic Attorney");
        banned.add("Jeweled Bird");
        banned.add("Rebirth");
        banned.add("Tempest Efreet");
        banned.add("Timmerian Fiends");

        // Potentially offensive cards are banned
        banned.add("Cleanse");
        banned.add("Crusade");
        banned.add("Imprison");
        banned.add("Invoke Prejudice");
        banned.add("Jihad");
        banned.add("Pradesh Gypsies");
        banned.add("Stone-Throwing Devils");

        // Cards from the "Living Dead" TV show crossover are banned
        banned.add("Daryl, Hunter of Walkers");
        banned.add("Glenn, the Voice of Calm");
        banned.add("Michonne, Ruthless Survivor");
        banned.add("Negan, the Cold-Blooded");
        banned.add("Rick, Steadfast Leader");
        banned.add("Lucille");

        // Power 9 except Time Twister
        banned.add("Black Lotus");
        banned.add("Ancestral Recall");
        banned.add("Mox Emerald");
        banned.add("Mox Jet");
        banned.add("Mox Pearl");
        banned.add("Mox Ruby");
        banned.add("Mox Sapphire");
        banned.add("Time Walk");

        // Remaining cards
        banned.add("Balance");
        banned.add("Birthing Pod");
        banned.add("Flash");
        banned.add("Gifts Ungiven");
        banned.add("Library of Alexandria");
        banned.add("Mana Crypt");
        banned.add("Mana Vault");
        banned.add("Mind Twist");
        banned.add("Mystical Tutor");
        banned.add("Natural Order");
        banned.add("Sensei's Divining Top");
        banned.add("Skullclamp");
        banned.add("Sol Ring");
        banned.add("Strip Mine");
        banned.add("Survival of the Fittest");
        banned.add("Time Vault");
        banned.add("Tinker");
        banned.add("Tolarian Academy");
        banned.add("Treasure Cruise");
        banned.add("True-Name Nemesis");
        banned.add("Umezawa's Jitte");
        banned.add("Vampiric Tutor");
    }
    
    @Override
    public int getDeckMinSize() {
        return 100;
    }

    @Override
    public boolean validate(Deck deck) {
        errorsList.clear();
	    
        // Parent class checks the banned list
        boolean valid = super.validate(deck);

        if (deck.getCards().size() < 100) {
            addError(DeckValidatorErrorType.DECK_SIZE, "Deck", "Must contain 100 or more singleton cards: has " + (deck.getCards().size()) + " cards");
            valid = false;
        }

        if (!deck.getSideboard().isEmpty()) {
            addError(DeckValidatorErrorType.DECK_SIZE, "Sideboard", "Sideboard can't contain any cards: has " + (deck.getSideboard().size()) + " cards");
            valid = false;
        }

        // Count main deck card occurrences
        // Note: Although a singleton format, the "any-number-of" cards are allowed
        Map<String, Integer> counts = new HashMap<>();
        countCards(counts, deck.getCards());
        valid = checkCounts(1, counts) && valid;

        return valid;
    }
}
