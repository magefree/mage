package mage.cards.mock;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.CardImpl;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author North
 */
public class MockCard extends CardImpl {

    // Needs to be here, as it is normally calculated from the
    // PlaneswalkerEntersWithLoyaltyAbility of the card... but the MockCard
    // only has MockAbilities.
    private int startingLoyalty;

    public MockCard(CardInfo card) {
        super(null, card.getName());
        this.cardNumber = card.getCardNumber();
        this.expansionSetCode = card.getSetCode();
        this.power = mageIntFromString(card.getPower());
        this.toughness = mageIntFromString(card.getToughness());
        this.rarity = card.getRarity();
        this.cardType = card.getTypes();
        this.subtype = card.getSubTypes();
        this.supertype = card.getSupertypes();

        this.usesVariousArt = card.usesVariousArt();

        this.manaCost = new ManaCostsImpl(join(card.getManaCosts()));

        this.color = card.getColor();

        this.frameColor = card.getFrameColor();
        this.frameStyle = card.getFrameStyle();

        this.splitCard = card.isSplitCard();
        this.flipCard = card.isFlipCard();

        this.transformable = card.isDoubleFaced();
        this.nightCard = card.isNightCard();
        if (card.getSecondSideName() != null && !card.getSecondSideName().isEmpty()) {
            this.secondSideCard = new MockCard(CardRepository.instance.findCardWPreferredSet(card.getSecondSideName(), card.getSetCode(), false));
        }

        if (this.isPlaneswalker()) {
            String startingLoyaltyString = card.getStartingLoyalty();
            if (startingLoyaltyString.isEmpty()) {
                //Logger.getLogger(MockCard.class).warn("Planeswalker `" + this.name + "` has empty starting loyalty.");
            } else {
                try {
                    this.startingLoyalty = Integer.parseInt(startingLoyaltyString);
                } catch (NumberFormatException e) {
                    Logger.getLogger(MockCard.class).warn("Planeswalker `" + this.name + "` starting loyalty in bad format: `" + startingLoyaltyString + "`.");
                }
            }
        }

        this.flipCardName = card.getFlipCardName();
        for (String ruleText : card.getRules()) {
            this.addAbility(textAbilityFromString(ruleText));
        }
    }

    public MockCard(final MockCard card) {
        super(card);
    }

    @Override
    public int getStartingLoyalty() {
        return startingLoyalty;
    }

    @Override
    public MockCard copy() {
        return new MockCard(this);
    }

    private MageInt mageIntFromString(String value) {
        try {
            int intValue = Integer.parseInt(value);
            return new MageInt(intValue);
        } catch (NumberFormatException e) {
            return new MageInt(0, value);
        }
    }

    private String join(List<String> strings) {
        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            sb.append(string);
        }
        return sb.toString();
    }

    private Ability textAbilityFromString(final String text) {
        return new MockAbility(text);
    }
}
