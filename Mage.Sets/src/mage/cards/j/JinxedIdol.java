package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.TargetPlayerGainControlSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class JinxedIdol extends CardImpl {

    public JinxedIdol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // At the beginning of your upkeep, Jinxed Idol deals 2 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DamageControllerEffect(2), TargetController.YOU, false));

        // Sacrifice a creature: Target opponent gains control of Jinxed Idol.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TargetPlayerGainControlSourceEffect(),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private JinxedIdol(final JinxedIdol card) {
        super(card);
    }

    @Override
    public JinxedIdol copy() {
        return new JinxedIdol(this);
    }

}
