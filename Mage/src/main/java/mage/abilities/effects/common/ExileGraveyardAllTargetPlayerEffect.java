package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

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
        staticText = "exile target player's graveyard";
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
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (targetPlayer == null || controller == null) {
            return false;
        }
        return toUniqueExile ?
                controller.moveCardsToExile(
                        targetPlayer.getGraveyard().getCards(game), source, game, true,
                        CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source)
                ) : controller.moveCards(targetPlayer.getGraveyard(), Zone.EXILED, source, game);
    }
}
