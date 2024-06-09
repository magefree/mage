
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class ScarscaleRitual extends CardImpl {

    public ScarscaleRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U/B}");

        // As an additional cost to cast Scarscale Ritual, put a -1/-1 counter on a creature you control.
        this.getSpellAbility().addCost(new ScarscaleRitualCost());

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));

    }

    private ScarscaleRitual(final ScarscaleRitual card) {
        super(card);
    }

    @Override
    public ScarscaleRitual copy() {
        return new ScarscaleRitual(this);
    }
}

class ScarscaleRitualCost extends CostImpl {

    public ScarscaleRitualCost() {
        this.text = "put a -1/-1 counter on a creature you control";
    }

    private ScarscaleRitualCost(final ScarscaleRitualCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controllerId, game)) {
            return permanent != null;
        }
        return false;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller != null) {
            Target target = new TargetControlledCreaturePermanent();
            target.withNotTarget(true);
            controller.chooseTarget(Outcome.UnboostCreature, target, ability, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.addCounters(CounterType.M1M1.createInstance(), controllerId, ability, game);
                game.informPlayers(controller.getLogName() + " puts a -1/-1 counter on " + permanent.getLogName());
                this.paid = true;
            }

        }
        return paid;
    }

    @Override
    public ScarscaleRitualCost copy() {
        return new ScarscaleRitualCost(this);
    }
}
