package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class ExileGraveyardAllTargetPlayerEffect extends OneShotEffect {

    private final boolean toUniqueExile;

    public ExileGraveyardAllTargetPlayerEffect() {
        this(false);
    }

    public ExileGraveyardAllTargetPlayerEffect(boolean toUniqueExile) {
        super(Outcome.Exile);
        this.toUniqueExile = toUniqueExile;
//        staticText = "exile target player's graveyard";
    }

    private ExileGraveyardAllTargetPlayerEffect(final ExileGraveyardAllTargetPlayerEffect effect) {
        super(effect);
        this.toUniqueExile = effect.toUniqueExile;
    }

    @Override
    public ExileGraveyardAllTargetPlayerEffect copy() {
        return new ExileGraveyardAllTargetPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        boolean result = false;
        for (UUID playerId : this.getTargetPointer().getTargets(game, source)) {
            Player targetPlayer = game.getPlayer(playerId);
            if (targetPlayer == null) {
                continue;
            }
            result |= toUniqueExile ?
                    controller.moveCardsToExile(
                            targetPlayer.getGraveyard().getCards(game), source, game, true,
                            CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source)
                    ) : controller.moveCards(targetPlayer.getGraveyard(), Zone.EXILED, source, game);

        }
        return result;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("exile ");
        sb.append(getTargetPointer().describeTargets(mode.getTargets(), "target player"));
        if (sb.toString().toLowerCase().endsWith("player") || sb.toString().toLowerCase().endsWith("opponent")) {
            if (getTargetPointer().isPlural(mode.getTargets())) {
                sb.append("s' graveyards");
            } else {
                sb.append("'s graveyard");
            }
        }
        return sb.toString();
    }
}
