
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetAdjustment;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author duncant
 */
public final class CandelabraOfTawnos extends CardImpl {

    public CandelabraOfTawnos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {X}, {T}: Untap X target lands.
        Effect effect = new UntapTargetEffect();
        effect.setText("untap X target lands");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{X}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_LANDS));
        ability.setTargetAdjustment(TargetAdjustment.X_TARGETS);
        this.addAbility(ability);
    }

    public CandelabraOfTawnos(final CandelabraOfTawnos card) {
        super(card);
    }

    @Override
    public CandelabraOfTawnos copy() {
        return new CandelabraOfTawnos(this);
    }
}
