
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author fireshoes
 */
public final class MengHuoBarbarianKing extends CardImpl {
    
    private static final FilterCreaturePermanent filterGreen = new FilterCreaturePermanent("green creatures you control");

    static {
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
        filterGreen.add(TargetController.YOU.getControllerPredicate());
    }

    public MengHuoBarbarianKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARBARIAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Other green creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filterGreen, true)));
    }

    private MengHuoBarbarianKing(final MengHuoBarbarianKing card) {
        super(card);
    }

    @Override
    public MengHuoBarbarianKing copy() {
        return new MengHuoBarbarianKing(this);
    }
}
