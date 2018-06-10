
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class YixlidJailer extends CardImpl {

    public YixlidJailer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Cards in graveyards lose all abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new YixlidJailerEffect()));
    }

    public YixlidJailer(final YixlidJailer card) {
        super(card);
    }

    @Override
    public YixlidJailer copy() {
        return new YixlidJailer(this);
    }

    static class YixlidJailerEffect extends ContinuousEffectImpl {

        YixlidJailerEffect() {
            super(Duration.WhileOnBattlefield, Outcome.LoseAbility);
            staticText = "Cards in graveyards lose all abilities.";
        }

        YixlidJailerEffect(final YixlidJailerEffect effect) {
            super(effect);
        }

        @Override
        public YixlidJailerEffect copy() {
            return new YixlidJailerEffect(this);
        }

        @Override
        public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
            if (layer == Layer.AbilityAddingRemovingEffects_6) {
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                        Player player = game.getPlayer(playerId);
                        if (player != null) {
                            for (Card card : player.getGraveyard().getCards(game)) {
                                if (card != null) {
                                    card.getAbilities(game).clear(); // Will the abilities ever come back????
                                    // TODO: Fix that (LevelX2)
                                    // game.getContinuousEffects().removeGainedEffectsForSource(card.getId());
                                    // game.getState().resetTriggersForSourceId(card.getId());
                                    Abilities abilities = game.getState().getAllOtherAbilities(card.getId());
                                    if (abilities != null) {
                                        abilities.clear();
                                    }
                                }
                            }
                        }
                    }
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return false;
        }

        @Override
        public boolean hasLayer(Layer layer) {
            return layer == Layer.AbilityAddingRemovingEffects_6;
        }
    }
}
