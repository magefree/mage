
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class RiteOfConsumption extends CardImpl {

    public RiteOfConsumption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // As an additional cost to cast Rite of Consumption, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT, false)));
        // Rite of Consumption deals damage equal to the sacrificed creature's power to target player. You gain life equal to the damage dealt this way.
        this.getSpellAbility().addEffect(new RiteOfConsumptionEffect());
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
    }

    private RiteOfConsumption(final RiteOfConsumption card) {
        super(card);
    }

    @Override
    public RiteOfConsumption copy() {
        return new RiteOfConsumption(this);
    }
}

class RiteOfConsumptionEffect extends OneShotEffect {

    public RiteOfConsumptionEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals damage equal to the sacrificed creature's power to target player or planeswalker. You gain life equal to the damage dealt this way";
    }

    public RiteOfConsumptionEffect(final RiteOfConsumptionEffect effect) {
        super(effect);
    }

    @Override
    public RiteOfConsumptionEffect copy() {
        return new RiteOfConsumptionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sacrificedCreature = null;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof SacrificeTargetCost) {
                    SacrificeTargetCost sacCost = (SacrificeTargetCost) cost;
                    for (Permanent permanent : sacCost.getPermanents()) {
                        sacrificedCreature = permanent;
                        break;
                    }
                }
            }
            if (sacrificedCreature != null) {
                int damage = sacrificedCreature.getPower().getValue();
                if (damage > 0) {
                    int damageDealt = game.damagePlayerOrPermanent(source.getFirstTarget(), damage, source.getSourceId(), source, game, false, true);
                    if (damageDealt > 0) {
                        controller.gainLife(damage, game, source);
                    }

                }
                return true;
            }
        }
        return false;
    }
}
