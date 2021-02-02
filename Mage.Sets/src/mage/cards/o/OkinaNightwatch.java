
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MoreCardsInHandThanOpponentsCondition;
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
 * @author LevelX2
 */
public final class OkinaNightwatch extends CardImpl {

    public OkinaNightwatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // As long as you have more cards in hand than each opponent, Okina Nightwatch gets +3/+3.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(3,3, Duration.WhileOnBattlefield),
                MoreCardsInHandThanOpponentsCondition.instance,
                "As long as you have more cards in hand than each opponent, Okina Nightwatch gets +3/+3"));
        this.addAbility(ability);
    }

    private OkinaNightwatch(final OkinaNightwatch card) {
        super(card);
    }

    @Override
    public OkinaNightwatch copy() {
        return new OkinaNightwatch(this);
    }
}
