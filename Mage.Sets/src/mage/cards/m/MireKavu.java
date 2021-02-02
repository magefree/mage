
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostSourceWhileControlsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author LoneFox
 */
public final class MireKavu extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Swamp");

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    public MireKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Mire Kavu gets +1/+1 as long as you control a Swamp.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceWhileControlsEffect(filter, 1, 1)));
    }

    private MireKavu(final MireKavu card) {
        super(card);
    }

    @Override
    public MireKavu copy() {
        return new MireKavu(this);
    }
}
