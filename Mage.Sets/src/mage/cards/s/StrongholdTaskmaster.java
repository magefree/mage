
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LoneFox

 */
public final class StrongholdTaskmaster extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("black creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public StrongholdTaskmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Other black creatures get -1/-1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(-1, -1,
            Duration.WhileOnBattlefield, filter, true)));
    }

    private StrongholdTaskmaster(final StrongholdTaskmaster card) {
        super(card);
    }

    @Override
    public StrongholdTaskmaster copy() {
        return new StrongholdTaskmaster(this);
    }
}
