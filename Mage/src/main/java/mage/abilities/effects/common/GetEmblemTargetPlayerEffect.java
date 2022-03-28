package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.players.Player;

import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public class GetEmblemTargetPlayerEffect extends OneShotEffect {

    private final Emblem emblem;

    public GetEmblemTargetPlayerEffect(Emblem emblem) {
        super(Outcome.Benefit);
        this.emblem = emblem;

    }

    public GetEmblemTargetPlayerEffect(final GetEmblemTargetPlayerEffect effect) {
        super(effect);
        this.emblem = effect.emblem;
    }

    @Override
    public GetEmblemTargetPlayerEffect copy() {
        return new GetEmblemTargetPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        if (sourceObject == null) {
            return false;
        }
        Player toPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (toPlayer == null) {
            return false;
        }
        game.addEmblem(emblem, sourceObject, toPlayer.getId());
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "target " + mode.getTargets().get(0).getTargetName() + " gets an emblem with \"" + emblem.getAbilities().getRules(null).stream().collect(Collectors.joining("; ")) + "\"";
    }
}
