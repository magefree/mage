
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class BlastingStation extends CardImpl {

    public BlastingStation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {tap}, Sacrifice a creature: Blasting Station deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Whenever a creature enters the battlefield, you may untap Blasting Station.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), StaticFilters.FILTER_PERMANENT_A_CREATURE, true));

    }

    private BlastingStation(final BlastingStation card) {
        super(card);
    }

    @Override
    public BlastingStation copy() {
        return new BlastingStation(this);
    }
}
