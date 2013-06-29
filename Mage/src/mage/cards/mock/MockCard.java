package mage.cards.mock;

import java.util.List;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.CardImpl;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;

/**
 * @author North
 */
public class MockCard extends CardImpl<MockCard> {
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
        this.splitCard = card.isSplitCard();
        this.flipCard = card.isFlipCard();

        this.canTransform = card.isDoubleFaced();
        this.nightCard = card.isNightCard();
        if (card.getSecondSideName() != null && !card.getSecondSideName().isEmpty()) {
            this.secondSideCard = new MockCard(CardRepository.instance.findCard(card.getSecondSideName()));
        }

        this.flipCardName = card.getFlipCardName();

        for(String ruleText: card.getRules()) {
            this.addAbility(textAbilityFromString(ruleText));
        }
    }

    public MockCard(final MockCard card) {
        super(card);
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
