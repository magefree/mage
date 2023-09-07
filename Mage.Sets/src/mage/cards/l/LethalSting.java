
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
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
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class LethalSting extends CardImpl {

    public LethalSting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // As an additional cost to cast Lethal Sting, put a -1/-1 counter on a creature you control.
        this.getSpellAbility().addCost(new LethalStingCost());

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private LethalSting(final LethalSting card) {
        super(card);
    }

    @Override
    public LethalSting copy() {
        return new LethalSting(this);
    }
}

class LethalStingCost extends CostImpl {

    public LethalStingCost() {
        this.text = "put a -1/-1 counter on a creature you control";
    }

    private LethalStingCost(final LethalStingCost cost) {
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
    public LethalStingCost copy() {
        return new LethalStingCost(this);
    }
}
