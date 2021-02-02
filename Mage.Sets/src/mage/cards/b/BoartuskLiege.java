
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author North
 */
public final class BoartuskLiege extends CardImpl {

    private static final FilterCreaturePermanent filterRed = new FilterCreaturePermanent("red creatures");
    private static final FilterCreaturePermanent filterGreen = new FilterCreaturePermanent("green creatures");

    static {
        filterRed.add(new ColorPredicate(ObjectColor.RED));
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public BoartuskLiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R/G}{R/G}{R/G}");
        this.subtype.add(SubType.GOBLIN, SubType.KNIGHT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(TrampleAbility.getInstance());
        // Other red creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterRed, true)));
        // Other green creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterGreen, true)));
    }

    private BoartuskLiege(final BoartuskLiege card) {
        super(card);
    }

    @Override
    public BoartuskLiege copy() {
        return new BoartuskLiege(this);
    }
}
