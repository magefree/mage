package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

/**
 *
 * @author awjackson
 */
public final class LeyLine extends CardImpl {

    public LeyLine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // At the beginning of each player's upkeep, that player may put a +1/+1 counter on target creature of their choice.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new LeyLineEffect(), TargetController.ANY, false);
        ability.setTargetAdjuster(LeyLineAdjuster.instance);
        this.addAbility(ability);
    }

    private LeyLine(final LeyLine card) {
        super(card);
    }

    @Override
    public LeyLine copy() {
        return new LeyLine(this);
    }
}

enum LeyLineAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        TargetCreaturePermanent target = new TargetCreaturePermanent();
        target.setAbilityController(ability.getControllerId());
        target.setTargetController(game.getActivePlayerId());
        ability.getTargets().clear();
        ability.getTargets().add(target);
    }
}

class LeyLineEffect extends OneShotEffect {

    public LeyLineEffect() {
        super(Outcome.BoostCreature);
        staticText = "that player may put a +1/+1 counter on target creature of their choice";
    }

    public LeyLineEffect(LeyLineEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        if (player.chooseUse(Outcome.BoostCreature, "Put a +1/+1 counter on " + permanent.getName() + "?", source, game)) {
            permanent.addCounters(CounterType.P1P1.createInstance(), player.getId(), source, game);
        }
        return true;
    }

    @Override
    public LeyLineEffect copy() {
        return new LeyLineEffect(this);
    }
}
