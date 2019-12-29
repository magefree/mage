
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class IchorExplosion extends CardImpl {

    public IchorExplosion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}");

        // As an additional cost to cast Ichor Explosion, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        // All creatures get -X/-X until end of turn, where X is the sacrificed creature's power.
        DynamicValue xValue = new IchorExplosionDynamicValue();
        this.getSpellAbility().addEffect(new BoostAllEffect(xValue, xValue, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, false, null, true));

    }

    public IchorExplosion(final IchorExplosion card) {
        super(card);
    }

    @Override
    public IchorExplosion copy() {
        return new IchorExplosion(this);
    }
}

class IchorExplosionDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Card sourceCard = game.getCard(sourceAbility.getSourceId());
        if (sourceCard != null) {
            for (Cost cost : sourceAbility.getCosts()) {
                if (cost instanceof SacrificeTargetCost) {
                    Permanent p = (Permanent) game.getLastKnownInformation(((SacrificeTargetCost) cost).getPermanents().get(0).getId(), Zone.BATTLEFIELD);
                    return -1 * p.getPower().getValue();
                }
            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the sacrificed creature's power";
    }

    @Override
    public String toString() {
        return "-X";
    }
}
