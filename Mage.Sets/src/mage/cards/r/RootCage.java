
package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author fireshoes
 */
public final class RootCage extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Mercenaries");

    static {
        filter.add(new SubtypePredicate(SubType.MERCENARY));
    }

    public RootCage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");

        // Mercenaries don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filter)));
    }

    public RootCage(final RootCage card) {
        super(card);
    }

    @Override
    public RootCage copy() {
        return new RootCage(this);
    }
}
