
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author nigelzor
 */
public final class ZuberiGoldenFeather extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.GRIFFIN, "Griffin creatures");

    public ZuberiGoldenFeather(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Other Griffin creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
    }

    private ZuberiGoldenFeather(final ZuberiGoldenFeather card) {
        super(card);
    }

    @Override
    public ZuberiGoldenFeather copy() {
        return new ZuberiGoldenFeather(this);
    }
}
