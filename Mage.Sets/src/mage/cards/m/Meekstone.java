package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author Quercitron
 */
public final class Meekstone extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures with power 3 or greater");
    
    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }
    
    public Meekstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // Creatures with power 3 or greater don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filter)));
    }

    private Meekstone(final Meekstone card) {
        super(card);
    }

    @Override
    public Meekstone copy() {
        return new Meekstone(this);
    }
}
