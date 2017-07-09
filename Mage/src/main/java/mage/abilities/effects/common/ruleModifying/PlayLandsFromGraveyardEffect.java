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

import java.util.UUID;

public class PlayLandsFromGraveyardEffect extends ContinuousEffectImpl {

    public PlayLandsFromGraveyardEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "You may play land cards from your graveyard";
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
            for (UUID cardId: player.getGraveyard()) {
                Card card = game.getCard(cardId);
                if(card != null && card.isLand()){
                    PlayLandFromGraveyardAbility ability = new PlayLandFromGraveyardAbility(card.getName());
                    ability.setSourceId(cardId);
                    ability.setControllerId(card.getOwnerId());
                    game.getState().addOtherAbility(card, ability);
                }
            }
            return true;
        }
        return false;
    }
}
