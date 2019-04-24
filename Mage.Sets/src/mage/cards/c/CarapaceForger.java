

package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author Loki
 */
public final class CarapaceForger extends CardImpl {

    public CarapaceForger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                        MetalcraftCondition.instance, "<i>Metalcraft</i> &mdash; {this} gets " +
                        "+2/+2 as long as you control three or more artifacts"
                )
        ));
    }

    public CarapaceForger(final CarapaceForger card) {
        super(card);
    }

    @Override
    public CarapaceForger copy() {
        return new CarapaceForger(this);
    }

}
