
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HeckbentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author stravant
 */
public final class ThresherLizard extends CardImpl {

    public ThresherLizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Thresher Lizard gets +1/+2 as long as you have one or fewer cards in hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new BoostSourceEffect(1, 2, Duration.WhileOnBattlefield),
                        HeckbentCondition.instance,
                        "{this} gets +1/+2 as long as you have one or fewer cards in hand")));
    }

    private ThresherLizard(final ThresherLizard card) {
        super(card);
    }

    @Override
    public ThresherLizard copy() {
        return new ThresherLizard(this);
    }
}
