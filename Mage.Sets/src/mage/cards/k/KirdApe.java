
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author jonubuu
 */
public final class KirdApe extends CardImpl {

    private static final String rule = "{this} gets +1/+2 as long as you control a Forest";
    private static final FilterLandPermanent filter = new FilterLandPermanent("a Forest");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public KirdApe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.APE);

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kird Ape gets +1/+2 as long as you control a Forest.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new BoostSourceEffect(1, 2, Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(filter), rule)));
    }

    private KirdApe(final KirdApe card) {
        super(card);
    }

    @Override
    public KirdApe copy() {
        return new KirdApe(this);
    }
}
