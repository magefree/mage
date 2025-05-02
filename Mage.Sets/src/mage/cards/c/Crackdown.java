
package mage.cards.c;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author fireshoes
 */
public final class Crackdown extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonwhite creatures with power 3 or greater");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.WHITE)));
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public Crackdown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");

        // Nonwhite creatures with power 3 or greater don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filter)));
    }

    private Crackdown(final Crackdown card) {
        super(card);
    }

    @Override
    public Crackdown copy() {
        return new Crackdown(this);
    }
}
