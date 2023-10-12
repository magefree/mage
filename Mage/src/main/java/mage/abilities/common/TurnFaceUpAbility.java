package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.AbilityType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class TurnFaceUpAbility extends SpecialAction {

    public TurnFaceUpAbility(Costs<Cost> costs) {
        this(costs, false);
    }

    public TurnFaceUpAbility(Costs<Cost> costs, boolean megamorph) {
        super(Zone.BATTLEFIELD);
        this.addEffect(new TurnFaceUpEffect(megamorph));
        this.addCost(costs);
        this.usesStack = false;
        this.abilityType = AbilityType.SPECIAL_ACTION;
        this.setRuleVisible(false); // will be made visible only to controller in CardView
        this.setWorksFaceDown(true);
    }

    protected TurnFaceUpAbility(final TurnFaceUpAbility ability) {
        super(ability);
    }

    @Override
    public TurnFaceUpAbility copy() {
        return new TurnFaceUpAbility(this);
    }
}

class TurnFaceUpEffect extends OneShotEffect {

    private final boolean megamorph;

    public TurnFaceUpEffect(boolean megamorph) {
        super(Outcome.Benefit);
        this.staticText = "Turn this face-down permanent face up" + (megamorph ? " and put a +1/+1 counter on it" : "");
        this.megamorph = megamorph;
    }

    protected TurnFaceUpEffect(final TurnFaceUpEffect effect) {
        super(effect);
        this.megamorph = effect.megamorph;
    }

    @Override
    public TurnFaceUpEffect copy() {
        return new TurnFaceUpEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (controller != null && card != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null) {
                if (sourcePermanent.turnFaceUp(source, game, source.getControllerId())) {
                    if (megamorph) {
                        sourcePermanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                    }
                    game.getState().setValue(source.getSourceId().toString() + "TurnFaceUpX", source.getManaCostsToPay().getX());
                    return true;
                }
            }
        }
        return false;
    }
}
