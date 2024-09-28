
package mage.cards.y;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.replacement.GraveyardFromAnywhereExileReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class YawgmothsWill extends CardImpl {

    public YawgmothsWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Until end of turn, you may play cards from your graveyard.
        this.getSpellAbility().addEffect(new CanPlayCardsFromGraveyardEffect());

        // If a card would be put into your graveyard from anywhere this turn, exile that card instead.
        this.getSpellAbility().addEffect(new GraveyardFromAnywhereExileReplacementEffect(Duration.EndOfTurn, null, true, false).concatBy("<br>"));
    }

    private YawgmothsWill(final YawgmothsWill card) {
        super(card);
    }

    @Override
    public YawgmothsWill copy() {
        return new YawgmothsWill(this);
    }
}

class CanPlayCardsFromGraveyardEffect extends ContinuousEffectImpl {

    CanPlayCardsFromGraveyardEffect() {
        this(Duration.EndOfTurn);
    }

    public CanPlayCardsFromGraveyardEffect(Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "Until end of turn, you may play cards from your graveyard";
    }

    private CanPlayCardsFromGraveyardEffect(final CanPlayCardsFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public CanPlayCardsFromGraveyardEffect copy() {
        return new CanPlayCardsFromGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.setPlayCardsFromGraveyard(true);
            return true;
        }
        return false;
    }

}
