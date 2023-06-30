
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.abilities.effects.common.continuous.CantCastMoreThanOneSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author Styxo
 */
public final class ColfenorsPlans extends CardImpl {

    public ColfenorsPlans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // When Colfenor's Plans enters the battlefield, exile the top seven cards of your library face down.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ColfenorsPlansExileEffect(), false));

        // You may look at and play cards exiled with Colfenor's Plans.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ColfenorsPlansPlayCardEffect()));
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ColfenorsPlansLookAtCardEffect()));

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipDrawStepEffect()));

        // You can't cast more than one spell each turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantCastMoreThanOneSpellEffect(TargetController.YOU)));

    }

    private ColfenorsPlans(final ColfenorsPlans card) {
        super(card);
    }

    @Override
    public ColfenorsPlans copy() {
        return new ColfenorsPlans(this);
    }
}

class ColfenorsPlansExileEffect extends OneShotEffect {

    public ColfenorsPlansExileEffect() {
        super(Outcome.DrawCard);
        staticText = "exile the top seven cards of your library face down";
    }

    public ColfenorsPlansExileEffect(final ColfenorsPlansExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards toExile = new CardsImpl(controller.getLibrary().getTopCards(game, 7));
            UUID exileId = CardUtil.getCardExileZoneId(game, source);
            controller.moveCardsToExile(toExile.getCards(game), source, game, false,
                    exileId, CardUtil.createObjectRealtedWindowTitle(source, game, null));
            ExileZone exileZone = game.getExile().getExileZone(exileId);
            if (exileZone != null) {
                for (Card card : exileZone.getCards(game)) {
                    if (card != null) {
                        card.setFaceDown(true, game);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ColfenorsPlansExileEffect copy() {
        return new ColfenorsPlansExileEffect(this);
    }
}

class ColfenorsPlansPlayCardEffect extends AsThoughEffectImpl {

    public ColfenorsPlansPlayCardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may play cards exiled with {this}";
    }

    public ColfenorsPlansPlayCardEffect(final ColfenorsPlansPlayCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ColfenorsPlansPlayCardEffect copy() {
        return new ColfenorsPlansPlayCardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId()) && game.getState().getZone(objectId) == Zone.EXILED) {
            ExileZone exileZone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
            return exileZone != null && exileZone.contains(objectId);
        }
        return false;
    }
}

class ColfenorsPlansLookAtCardEffect extends AsThoughEffectImpl {

    public ColfenorsPlansLookAtCardEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may look at cards exiled with {this}";
    }

    public ColfenorsPlansLookAtCardEffect(final ColfenorsPlansLookAtCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ColfenorsPlansLookAtCardEffect copy() {
        return new ColfenorsPlansLookAtCardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null) {
                MageObject sourceObject = game.getObject(source);
                if (sourceObject == null) {
                    return false;
                }
                UUID exileId = CardUtil.getCardExileZoneId(game, source);
                ExileZone exile = game.getExile().getExileZone(exileId);
                return exile != null && exile.contains(objectId);
            }
        }
        return false;
    }

}
