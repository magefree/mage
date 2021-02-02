
package mage.cards.m;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author fireshoes
 */
public final class MagistratesVeto extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("white creatures and blue creatures");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLUE)));
    }

    public MagistratesVeto(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // White creatures and blue creatures can't block.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockAllEffect(filter, Duration.WhileOnBattlefield)));
    }

    private MagistratesVeto(final MagistratesVeto card) {
        super(card);
    }

    @Override
    public MagistratesVeto copy() {
        return new MagistratesVeto(this);
    }
}
