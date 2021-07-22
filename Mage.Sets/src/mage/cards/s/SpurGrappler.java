
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;

/**
 *
 * @author LoneFox
 */
public final class SpurGrappler extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public SpurGrappler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Spur Grappler gets +2/+1 as long as you control no untapped lands.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
            new ConditionalContinuousEffect(new BoostSourceEffect(2, 1, Duration.WhileOnBattlefield),
            new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter)),
            "{this} gets +2/+1 as long as you control no untapped lands")));
    }

    private SpurGrappler(final SpurGrappler card) {
        super(card);
    }

    @Override
    public SpurGrappler copy() {
        return new SpurGrappler(this);
    }
}
