
package mage.cards.t;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.OrCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author L_J
 */
public final class TidalControl extends CardImpl {
    private static final FilterSpell filter = new FilterSpell("red or green spell");
    static{
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.RED), new ColorPredicate(ObjectColor.GREEN)));
    }

    public TidalControl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");

        // Cumulative upkeep-Pay {2}.
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{2}")));

        // Pay 2 life or {2}: Counter target red or green spell. Any player may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new OrCost("pay 2 life or pay {2}", new PayLifeCost(2), new ManaCostsImpl("{2}")));
        ability.addTarget(new TargetSpell(filter));
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
    }

    private TidalControl(final TidalControl card) {
        super(card);
    }

    @Override
    public TidalControl copy() {
        return new TidalControl(this);
    }
}
