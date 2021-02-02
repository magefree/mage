
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LoneFox
 */
public final class RadiantEssence extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a black permanent");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public RadiantEssence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Radiant Essence gets +1/+2 as long as an opponent controls a black permanent.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
            new ConditionalContinuousEffect(new BoostSourceEffect(1, 2, Duration.WhileOnBattlefield),
            new OpponentControlsPermanentCondition(filter),
            "{this} gets +1/+2 as long as an opponent controls a black permanent")));
    }

    private RadiantEssence(final RadiantEssence card) {
        super(card);
    }

    @Override
    public RadiantEssence copy() {
        return new RadiantEssence(this);
    }
}
