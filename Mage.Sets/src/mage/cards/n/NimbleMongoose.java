package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author jonubuu
 */
public final class NimbleMongoose extends CardImpl {

    public NimbleMongoose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.MONGOOSE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Shroud
        this.addAbility(ShroudAbility.getInstance());

        // Threshold - Nimble Mongoose gets +2/+2 as long as seven or more cards are in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), ThresholdCondition.instance,
                "{this} gets +2/+2 as long as seven or more cards are in your graveyard"
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private NimbleMongoose(final NimbleMongoose card) {
        super(card);
    }

    @Override
    public NimbleMongoose copy() {
        return new NimbleMongoose(this);
    }
}
