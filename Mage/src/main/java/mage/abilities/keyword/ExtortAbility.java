

package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * FAQ 2013/01/11
 *
 * 702.99. Extort
 *
 * 702.99a Extort is a triggered ability. "Extort" means "Whenever you cast a spell,
 * you may pay White or Black Mana. If you do, each opponent loses 1 life and you gain
 * life equal to the total life lost this way."
 *
 * 702.99b If a permanent has multiple instances of extort, each triggers separately.
 *
 * @author LevelX2
 */
public class ExtortAbility extends TriggeredAbilityImpl {

    public ExtortAbility() {
        super(Zone.BATTLEFIELD, new ExtortEffect(), false);
    }

    public ExtortAbility(final ExtortAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId());
    }

    @Override
    public String getRule() {
        return "Extort <i>(Whenever you cast a spell, you may pay {WB}. If you do, each opponent loses 1 life and you gain that much life.)</i>";
    }

    @Override
    public ExtortAbility copy() {
        return new ExtortAbility(this);
    }
}

class ExtortEffect extends OneShotEffect {
    public ExtortEffect() {
        super(Outcome.Damage);
        staticText = "each opponent loses 1 life and you gain that much life";
    }

    public ExtortEffect(final ExtortEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            if (player.chooseUse(Outcome.Damage, new StringBuilder("Extort opponents? (").append(permanent.getName()).append(')').toString(), source, game)) {
                Cost cost = new ManaCostsImpl<>("{W/B}");
                if (cost.pay(source, game, source, player.getId(), false, null)) {
                    int loseLife = 0;
                    for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                        loseLife += game.getPlayer(opponentId).loseLife(1, game, source, false);
                    }
                    if (loseLife > 0) {
                        game.getPlayer(source.getControllerId()).gainLife(loseLife, game, source);
                    }
                    if (!game.isSimulation())
                        game.informPlayers(new StringBuilder(permanent.getName()).append(" extorted opponents ").append(loseLife).append(" life").toString());
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ExtortEffect copy() {
        return new ExtortEffect(this);
    }
}
