
package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SpringingTiger extends CardImpl {

    public SpringingTiger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Threshold - Springing Tiger gets +2/+2 as long as seven or more cards are in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), ThresholdCondition.instance,
                "{this} gets +2/+2 as long as seven or more cards are in your graveyard"
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private SpringingTiger(final SpringingTiger card) {
        super(card);
    }

    @Override
    public SpringingTiger copy() {
        return new SpringingTiger(this);
    }
}
