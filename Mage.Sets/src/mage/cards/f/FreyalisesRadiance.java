
package mage.cards.f;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class FreyalisesRadiance extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("snow permanents");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public FreyalisesRadiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Cumulative upkeep {2}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{2}")));

        // Snow permanents don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filter)));
    }

    private FreyalisesRadiance(final FreyalisesRadiance card) {
        super(card);
    }

    @Override
    public FreyalisesRadiance copy() {
        return new FreyalisesRadiance(this);
    }
}
