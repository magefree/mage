
package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.replacement.GraveyardFromAnywhereExileReplacementEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author Quercitron
 */
public final class ForbiddenCrypt extends CardImpl {

    public ForbiddenCrypt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}{B}");

        // If you would draw a card, return a card from your graveyard to your hand instead. If you can't, you lose the game.
        this.addAbility(new SimpleStaticAbility(new ForbiddenCryptDrawCardReplacementEffect()));
        // If a card would be put into your graveyard from anywhere, exile that card instead.
        this.addAbility(new SimpleStaticAbility(new GraveyardFromAnywhereExileReplacementEffect(true, false)));
    }

    private ForbiddenCrypt(final ForbiddenCrypt card) {
        super(card);
    }

    @Override
    public ForbiddenCrypt copy() {
        return new ForbiddenCrypt(this);
    }
}

class ForbiddenCryptDrawCardReplacementEffect extends ReplacementEffectImpl {

    ForbiddenCryptDrawCardReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "If you would draw a card, return a card from your graveyard to your hand instead. If you can't, you lose the game";
    }

    private ForbiddenCryptDrawCardReplacementEffect(final ForbiddenCryptDrawCardReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ForbiddenCryptDrawCardReplacementEffect copy() {
        return new ForbiddenCryptDrawCardReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            boolean cardReturned = false;
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard();
            target.withNotTarget(true);
            if (target.canChoose(controller.getId(), source, game)) {
                if (target.choose(Outcome.ReturnToHand, controller.getId(), source.getSourceId(), source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        controller.moveCards(card, Zone.HAND, source, game);
                        cardReturned = true;
                    }
                }
            }
            if (!cardReturned) {
                game.informPlayers(controller.getLogName() + " can't return a card from graveyard to hand.");
                controller.lost(game);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }

}