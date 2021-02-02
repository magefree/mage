
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author nantuko
 */
public final class GallowsWarden extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Spirit creatures");

    static {
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public GallowsWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // Other Spirit creatures you control get +0/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(0, 1, Duration.WhileOnBattlefield, filter, true)));
    }

    private GallowsWarden(final GallowsWarden card) {
        super(card);
    }

    @Override
    public GallowsWarden copy() {
        return new GallowsWarden(this);
    }
}
