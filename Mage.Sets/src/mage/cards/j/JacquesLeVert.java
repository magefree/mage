
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author fireshoes
 */
public final class JacquesLeVert extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Green creatures");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public JacquesLeVert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Green creatures you control get +0/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(0,2, Duration.WhileOnBattlefield, filter)));
    }

    private JacquesLeVert(final JacquesLeVert card) {
        super(card);
    }

    @Override
    public JacquesLeVert copy() {
        return new JacquesLeVert(this);
    }
}
