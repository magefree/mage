package mage.cards;

import com.sun.deploy.util.StringUtils;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.AbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.AbilityType;
import mage.constants.Zone;
import mage.watchers.Watcher;

/**
 * @author North
 */
public class MockCard extends CardImpl {
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

        this.manaCost = new ManaCostsImpl(StringUtils.join(card.getManaCosts(), ""));

        this.color = card.getColor();
        this.splitCard = card.isSplitCard();
        this.flipCard = card.isFlipCard();

        this.canTransform = card.isDoubleFaced();
        this.nightCard = card.isNightCard();
        if (card.getSecondSideName() != null && !card.getSecondSideName().isEmpty()) {
            this.secondSideCard = new MockCard(CardRepository.instance.findCard(card.getSecondSideName()));
        }

        this.flipCardName = card.getFlipCardName();

        for(String text: card.getRules()) {
            this.addAbility(textAbilityFromString(text));
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

    private Ability textAbilityFromString(final String text) {
        return new AbilityImpl(AbilityType.STATIC, Zone.ALL) {
            @Override
            public AbilityImpl copy() {
                return this;
            }

            @Override
            public String getRule(boolean all) {
                return text;
            }
        };
    }
}
