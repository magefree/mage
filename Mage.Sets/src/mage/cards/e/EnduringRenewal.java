package mage.cards.e;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author anonymous
 */
public final class EnduringRenewal extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a creature");

    public EnduringRenewal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // Play with your hand revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithHandRevealedEffect()));

        // If you would draw a card, reveal the top card of your library instead. If it's a creature card, put it into your graveyard. Otherwise, draw a card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EnduringRenewalReplacementEffect()));

        // Whenever a creature is put into your graveyard from the battlefield, return it to your hand.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("return it to your hand");
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(effect, false, filter, true, true));
    }

    public EnduringRenewal(final EnduringRenewal card) {
        super(card);
    }

    @Override
    public EnduringRenewal copy() {
        return new EnduringRenewal(this);
    }
}

class EnduringRenewalReplacementEffect extends ReplacementEffectImpl {

    public EnduringRenewalReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card, reveal the top card of your library instead. If it's a creature card, put it into your graveyard. Otherwise, draw a card";
    }

    public EnduringRenewalReplacementEffect(final EnduringRenewalReplacementEffect effect) {
        super(effect);
    }

    @Override
    public EnduringRenewalReplacementEffect copy() {
        return new EnduringRenewalReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card != null) {
            Cards cards = new CardsImpl(card);
            controller.revealCards("Top card of " + controller.getName() + "'s library", cards, game);
            if (card.isCreature()) {
                controller.moveCards(card, Zone.GRAVEYARD, source, game);
            } else {
                // This is still replacing the draw, so we still return true
                controller.drawCards(1, source.getSourceId(), game, event.getAppliedEffects());
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

class PlayWithHandRevealedEffect extends ContinuousEffectImpl {

    public PlayWithHandRevealedEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Detriment);
        staticText = "Play with your hand revealed";
    }

    public PlayWithHandRevealedEffect(final PlayWithHandRevealedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            controller.revealCards(controller.getName(), controller.getHand(), game, false);
            return true;
        }
        return false;
    }

    @Override
    public PlayWithHandRevealedEffect copy() {
        return new PlayWithHandRevealedEffect(this);
    }
}

class EnduringRenewalEffect extends OneShotEffect {

    public EnduringRenewalEffect() {
        super(Outcome.ReturnToHand);
        staticText = "return it to your hand";
    }

    public EnduringRenewalEffect(final EnduringRenewalEffect effect) {
        super(effect);
    }

    @Override
    public EnduringRenewalEffect copy() {
        return new EnduringRenewalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID creatureId = (UUID) getValue("returningCreature");
        Permanent creature = game.getPermanent(creatureId);
        if (creature != null) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                creature.moveToZone(Zone.HAND, source.getSourceId(), game, false);
            }
            return true;
        }
        return false;
    }
}
