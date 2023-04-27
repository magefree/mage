
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class Necropotence extends CardImpl {

    public Necropotence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}{B}");

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipDrawStepEffect()));
        // Whenever you discard a card, exile that card from your graveyard.
        Effect effect = new ExileTargetEffect(null, "", Zone.GRAVEYARD);
        effect.setText("exile that card from your graveyard");
        this.addAbility(new NecropotenceTriggeredAbility(effect));
        // Pay 1 life: Exile the top card of your library face down. Put that card into your hand at the beginning of your next end step.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new NecropotenceEffect(), new PayLifeCost(1)));

    }

    private Necropotence(final Necropotence card) {
        super(card);
    }

    @Override
    public Necropotence copy() {
        return new Necropotence(this);
    }
}

class NecropotenceTriggeredAbility extends TriggeredAbilityImpl {

    NecropotenceTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever you discard a card, ");
    }

    NecropotenceTriggeredAbility(final NecropotenceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NecropotenceTriggeredAbility copy() {
        return new NecropotenceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (isControlledBy(event.getPlayerId())) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getTargetId()));
            return true;
        }
        return false;
    }
}

class NecropotenceEffect extends OneShotEffect {

    public NecropotenceEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile the top card of your library face down. Put that card into your hand at the beginning of your next end step";
    }

    public NecropotenceEffect(final NecropotenceEffect effect) {
        super(effect);
    }

    @Override
    public NecropotenceEffect copy() {
        return new NecropotenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null && controller.moveCardsToExile(card, source, game, false,
                    CardUtil.getCardExileZoneId(game, source),
                    CardUtil.createObjectRealtedWindowTitle(source, game, null))) {
                card.setFaceDown(true, game);
                Effect returnToHandEffect = new ReturnToHandTargetEffect();
                returnToHandEffect.setText("put that face down card into your hand");
                returnToHandEffect.setTargetPointer(new FixedTarget(card, game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(returnToHandEffect, TargetController.YOU), source);
                return true;
            }
            return true;
        }
        return false;
    }
}
