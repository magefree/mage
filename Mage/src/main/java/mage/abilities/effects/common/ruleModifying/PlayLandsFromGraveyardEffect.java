package mage.abilities.effects.common.ruleModifying;

import mage.abilities.Ability;
import mage.abilities.common.PlayLandFromGraveyardAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

public class PlayLandsFromGraveyardEffect extends ContinuousEffectImpl {

    public PlayLandsFromGraveyardEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "You may play lands from your graveyard";
    }

    public PlayLandsFromGraveyardEffect(final PlayLandsFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public PlayLandsFromGraveyardEffect copy() {
        return new PlayLandsFromGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Card card : player.getGraveyard().getCards(game)) {
                if (card != null && card.isLand()) {
                    PlayLandFromGraveyardAbility ability = new PlayLandFromGraveyardAbility(card.getName());
                    ability.setSourceId(card.getId());
                    ability.setControllerId(card.getOwnerId());
                    game.getState().addOtherAbility(card, ability);
                }
            }
            return true;
        }
        return false;
    }
}
