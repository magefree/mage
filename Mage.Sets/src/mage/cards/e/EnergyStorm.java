
package mage.cards.e;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.effects.common.PreventAllDamageByAllObjectsEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author TheElk801
 */
public final class EnergyStorm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public EnergyStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));

        // Prevent all damage that would be dealt by instant and sorcery spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new PreventAllDamageByAllObjectsEffect(StaticFilters.FILTER_SPELLS_INSTANT_OR_SORCERY, Duration.WhileOnBattlefield, false)
        ));

        // Creatures with flying don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filter)));
    }

    private EnergyStorm(final EnergyStorm card) {
        super(card);
    }

    @Override
    public EnergyStorm copy() {
        return new EnergyStorm(this);
    }
}
