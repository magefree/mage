
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * 702.84. Annihilator 702.84a Annihilator is a triggered ability. "Annihilator
 * N" means "Whenever this creature attacks, defending player sacrifices N
 * permanents."
 *
 * 702.84b If a creature has multiple instances of annihilator, each triggers
 * separately.
 *
 * @author maurer.it_at_gmail.com
 */
public class AnnihilatorAbility extends TriggeredAbilityImpl {

    int count;

    public AnnihilatorAbility(int count) {
        super(Zone.BATTLEFIELD, new AnnihilatorEffect(count), false);
        this.count = count;
    }

    public AnnihilatorAbility(final AnnihilatorAbility ability) {
        super(ability);
        this.count = ability.count;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(sourceId, game);
            if (defendingPlayerId != null) {
                // the id has to be set here because the source can be leave battlefield
                getEffects().forEach((effect) -> {
                    effect.setValue("defendingPlayerId", defendingPlayerId);
                });
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Annihilator " + count + " <i>(Whenever this creature attacks, defending player sacrifices "
                + (count == 1 ? "a permanent" : CardUtil.numberToText(count) + " permanents") + ".)</i>";
    }

    @Override
    public AnnihilatorAbility copy() {
        return new AnnihilatorAbility(this);
    }

}

class AnnihilatorEffect extends OneShotEffect {

    private final int count;

    AnnihilatorEffect(int count) {
        super(Outcome.Sacrifice);
        this.count = count;
    }

    AnnihilatorEffect(AnnihilatorEffect effect) {
        super(effect);
        this.count = effect.count;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID defendingPlayerId = (UUID) getValue("defendingPlayerId");
        Player player = null;
        if (defendingPlayerId != null) {
            player = game.getPlayer(defendingPlayerId);
        }
        if (player != null) {
            int amount = Math.min(count, game.getBattlefield().countAll(new FilterControlledPermanent(), player.getId(), game));
            if (amount > 0) {
                Target target = new TargetControlledPermanent(amount, amount, new FilterControlledPermanent(), true);
                if (target.canChoose(player.getId(), source, game)) {
                    while (player.canRespond()
                            && target.canChoose(player.getId(), source, game)
                            && !target.isChosen()) {
                        player.choose(Outcome.Sacrifice, target, source, game);
                    }
                    target.getTargets().stream()
                            .map(game::getPermanent)
                            .filter(Objects::nonNull)
                            .forEach(permanent -> permanent.sacrifice(source, game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public AnnihilatorEffect copy() {
        return new AnnihilatorEffect(this);
    }

}
