package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.PlayWithHandRevealedEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class EnduringRenewal extends CardImpl {

    public EnduringRenewal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // Play with your hand revealed.
        this.addAbility(new SimpleStaticAbility(new PlayWithHandRevealedEffect(TargetController.YOU)));

        // If you would draw a card, reveal the top card of your library instead. If it's a creature card, put it into your graveyard. Otherwise, draw a card.
        this.addAbility(new SimpleStaticAbility(new EnduringRenewalReplacementEffect()));

        // Whenever a creature is put into your graveyard from the battlefield, return it to your hand.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect().setText("return it to your hand"),
                false, StaticFilters.FILTER_PERMANENT_A_CREATURE, true, true
        ));
    }

    private EnduringRenewal(final EnduringRenewal card) {
        super(card);
    }

    @Override
    public EnduringRenewal copy() {
        return new EnduringRenewal(this);
    }
}

class EnduringRenewalReplacementEffect extends ReplacementEffectImpl {

    EnduringRenewalReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card, reveal the top card of your library instead. " +
                "If it's a creature card, put it into your graveyard. Otherwise, draw a card";
    }

    private EnduringRenewalReplacementEffect(final EnduringRenewalReplacementEffect effect) {
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
        if (card == null) {
            return false;
        }
        Cards cards = new CardsImpl(card);
        controller.revealCards("Top card of " + controller.getName() + "'s library", cards, game);
        if (card.isCreature(game)) {
            controller.moveCards(card, Zone.GRAVEYARD, source, game);
        } else {
            // This is still replacing the draw, so we still return true
            controller.drawCards(1, source, game, event);
        }
        return true;
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
