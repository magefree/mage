
package mage.cards.p;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author rscoates
 */
public final class PrecognitionField extends CardImpl {

    public PrecognitionField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // You may look at the top card of your library.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PrecognitionFieldTopCardRevealedEffect()));

        // You may cast the top card of your library if it's an instant or sorcery card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PrecognitionFieldTopCardCastEffect()));

        // {3}: Exile the top card of your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new PrecognitionFieldExileEffect(), new GenericManaCost(3)));
    }

    public PrecognitionField(final PrecognitionField card) {
        super(card);
    }

    @Override
    public PrecognitionField copy() {
        return new PrecognitionField(this);
    }
}

class PrecognitionFieldTopCardRevealedEffect extends ContinuousEffectImpl {

    public PrecognitionFieldTopCardRevealedEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "You may look at the top card of your library any time.";
    }

    public PrecognitionFieldTopCardRevealedEffect(final PrecognitionFieldTopCardRevealedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card topCard = controller.getLibrary().getFromTop(game);
            if (topCard != null) {
                MageObject precognitionField = source.getSourceObject(game);
                if (precognitionField != null) {
                    controller.lookAtCards("Top card of " + precognitionField.getIdName() + " controller's library", topCard, game);
                }
            }
        }
        return true;
    }

    @Override
    public PrecognitionFieldTopCardRevealedEffect copy() {
        return new PrecognitionFieldTopCardRevealedEffect(this);
    }
}

class PrecognitionFieldTopCardCastEffect extends AsThoughEffectImpl {

    public PrecognitionFieldTopCardCastEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may cast the top card of your library if it's an instant or sorcery card.";
    }

    public PrecognitionFieldTopCardCastEffect(final PrecognitionFieldTopCardCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PrecognitionFieldTopCardCastEffect copy() {
        return new PrecognitionFieldTopCardCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null) {
                Player controller = game.getPlayer(affectedControllerId);
                if (controller != null) {
                    Card topCard = controller.getLibrary().getFromTop(game);
                    MageObject precognitionField = game.getObject(source.getSourceId());
                    if (precognitionField != null
                            && topCard != null) {
                        return topCard == card
                                && (topCard.isInstant() || topCard.isSorcery())
                                && topCard.getSpellAbility() != null
                                && topCard.getSpellAbility().spellCanBeActivatedRegularlyNow(controller.getId(), game);
                    }
                }
            }
        }
        return false;
    }
}

class PrecognitionFieldExileEffect extends OneShotEffect {

    public PrecognitionFieldExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library";
    }

    public PrecognitionFieldExileEffect(final PrecognitionFieldExileEffect effect) {
        super(effect);
    }

    @Override
    public PrecognitionFieldExileEffect copy() {
        return new PrecognitionFieldExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.moveCards(card, Zone.EXILED, source, game);
            }
            return true;
        }
        return false;
    }
}
