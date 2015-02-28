/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.deck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;
import mage.constants.CardType;
import mage.filter.FilterMana;
import mage.util.CardUtil;

/**
 *
 * @author nickmyers
 */
public class TinyLeaders extends DeckValidator {
    
    protected  List<String> banned = new ArrayList<>();
    protected  List<String> bannedCommander = new ArrayList<>();
    
    public TinyLeaders(String name) {
        super(name);
    }
    
    public TinyLeaders() {
        this("Tiny Leaders");
        banned.add("Ancestral Recall");
        banned.add("Black Lotus");
        banned.add("Black Vise");
        banned.add("Channel");
        banned.add("Counterbalance");
        banned.add("Demonic Tutor");
        banned.add("Earthcraft");
        banned.add("Edric, Spymaster of Trest");
        banned.add("Fastbond");
        banned.add("Goblin Recruiter");
        banned.add("Hermit Druid");
        banned.add("Imperial Seal");
        banned.add("Library of Alexandria");
        banned.add("Karakas");
        banned.add("Mana Crypt");
        banned.add("Mana Drain");
        banned.add("Mana Vault");
        banned.add("Metalworker");
        banned.add("Mind Twist");
        banned.add("Mishra's Workshop");
        banned.add("Mox Emerald");
        banned.add("Mox Jet");
        banned.add("Mox Pearl");
        banned.add("Mox Ruby");
        banned.add("Mox Sapphire");
        banned.add("Necropotence");
        banned.add("Painter's Servant");
        banned.add("Shahrazad");
        banned.add("Skullclamp");
        banned.add("Sol Ring");
        banned.add("Strip Mine");
        banned.add("Survival of the Fittest");
        banned.add("Sword of Body and Mind");
        banned.add("Time Vault");
        banned.add("Time Walk");
        banned.add("Timetwister");
        banned.add("Tolarian Academy");
        banned.add("Umezawa's Jitte");
        banned.add("Vampiric Tutor");
        banned.add("Wheel of Fortune");
        banned.add("Yawgmoth's Will");
        
        bannedCommander.add("Erayo, Soratami Ascendant");
        bannedCommander.add("Rofellos, Llanowar Emissary");
        bannedCommander.add("Derevi, Empyrical Tactician");
        
    }
    
    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;
        
        // Check CMC <= 3 of main deck
        for(Card card : deck.getCards()) {
            if(card.getManaCost().convertedManaCost() > 3) {
                invalid.put("Mana Cost", "Converted Mana Cost of " + card.getName() + " is too high");
                valid = false;
            }
        }
        
        // Check CMC <=3 for commander
        for(Card card : deck.getSideboard()) {
            if(card.getManaCost().convertedManaCost() > 3) {
                invalid.put("Commander Mana Cost", "Converted Mana Cost of " + card.getName() + " is too high");
            }
        }

        if (deck.getCards().size() != 49) {
            invalid.put("Deck", "Must contain 49 cards: has " + deck.getCards().size() + " cards");
            valid = false;
        }

        List<String> basicLandNames = new ArrayList<>(Arrays.asList("Forest", "Island", "Mountain", "Swamp", "Plains",
                "Snow-Covered Forest", "Snow-Covered Island", "Snow-Covered Mountain", "Snow-Covered Swamp", "Snow-Covered Plains"));
        Map<String, Integer> counts = new HashMap<>();
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > 1) {
                if (!basicLandNames.contains(entry.getKey()) && !entry.getKey().equals("Relentless Rats") && !entry.getKey().equals("Shadowborn Apostle")) {
                    invalid.put(entry.getKey(), "Too many: " + entry.getValue());
                    valid = false;
                }
            }
        }

        for (String bannedCard : banned) {
            if (counts.containsKey(bannedCard)) {
                invalid.put(bannedCard, "Banned");
                valid = false;
            }
        }

        if (deck.getSideboard().size() == 1) {
            Card commander = (Card) deck.getSideboard().toArray()[0];
            if (commander == null) {
                invalid.put("Commander", "Commander invalid ");
                return false;
            }
            if ((commander.getCardType().contains(CardType.CREATURE) && commander.getSupertype().contains("Legendary")) ||
                    (commander.getCardType().contains(CardType.PLANESWALKER) && commander.getAbilities().contains(CanBeYourCommanderAbility.getInstance()))) {
                if (!bannedCommander.contains(commander.getName())) {
                    FilterMana color = CardUtil.getColorIdentity(commander);
                    for (Card card : deck.getCards()) {
                        if (!cardHasValideColor(color, card)) {
                            invalid.put(card.getName(), "Invalid color (" + commander.getName() +")");
                            valid = false;
                        }
                    }
                } else {
                    invalid.put("Commander", "Commander banned (" + commander.getName() +")");
                    valid = false;
                }
            } else {
                invalid.put("Commander", "Commander invalid (" + commander.getName() +")");
                valid = false;
            }
        } else {
            invalid.put("Commander", "Sideboard must contain only the commander");
        }

        return valid;
    }

    public boolean cardHasValideColor(FilterMana commander, Card card) {
        FilterMana cardColor = CardUtil.getColorIdentity(card);
        if (cardColor.isBlack() && !commander.isBlack()
                || cardColor.isBlue() && !commander.isBlue()
                || cardColor.isGreen() && !commander.isGreen()
                || cardColor.isRed() && !commander.isRed()
                || cardColor.isWhite() && !commander.isWhite()) {
            return false;
        }
        return true;
    }
    
}
