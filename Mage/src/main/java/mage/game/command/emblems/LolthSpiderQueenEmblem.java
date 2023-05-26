package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.players.Player;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.Objects;

/**
 * @author TheElk801
 */
public final class LolthSpiderQueenEmblem extends Emblem {

    // −8: You get an emblem with "Whenever an opponent is dealt combat damage by one or more creatures you control, if that player lost less than 8 life this turn, they lose life equal to the difference."
    public LolthSpiderQueenEmblem() {
        super("Emblem Lolth");
        this.getAbilities().add(new ConditionalInterveningIfTriggeredAbility(
                new DealCombatDamageControlledTriggeredAbility(
                        Zone.COMMAND, new LolthSpiderQueenEmblemEffect(), true, true
                ), LolthSpiderQueenEmblemCondition.instance, "Whenever an opponent " +
                "is dealt combat damage by one or more creatures you control, " +
                "if that player lost less than 8 life this turn, they lose life equal to the difference."
        ));
    }

    private LolthSpiderQueenEmblem(final LolthSpiderQueenEmblem card) {
        super(card);
    }

    @Override
    public LolthSpiderQueenEmblem copy() {
        return new LolthSpiderQueenEmblem(this);
    }
}

enum LolthSpiderQueenEmblemCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = source
                .getEffects()
                .stream()
                .map(Effect::getTargetPointer)
                .map(tp -> tp.getFirst(game, source))
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        return player != null && watcher != null && watcher.getLifeLost(player.getId()) < 8;
    }
}

class LolthSpiderQueenEmblemEffect extends OneShotEffect {

    LolthSpiderQueenEmblemEffect() {
        super(Outcome.Benefit);
    }

    private LolthSpiderQueenEmblemEffect(final LolthSpiderQueenEmblemEffect effect) {
        super(effect);
    }

    @Override
    public LolthSpiderQueenEmblemEffect copy() {
        return new LolthSpiderQueenEmblemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        return player != null && watcher != null && player.loseLife(
                Math.max(8 - watcher.getLifeLost(player.getId()), 0), game, source, false
        ) > 0;
    }
}
