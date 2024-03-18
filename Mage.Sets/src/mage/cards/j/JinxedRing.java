package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.TargetPlayerGainControlSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class JinxedRing extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a nontoken permanent");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public JinxedRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever a nontoken permanent is put into your graveyard from the battlefield, Jinxed Ring deals 1 damage to you.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new DamageControllerEffect(1), false, filter, false, true));

        // Sacrifice a creature: Target opponent gains control of Jinxed Ring.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TargetPlayerGainControlSourceEffect(),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private JinxedRing(final JinxedRing card) {
        super(card);
    }

    @Override
    public JinxedRing copy() {
        return new JinxedRing(this);
    }
}
