package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class Necrodominance extends CardImpl {

    public Necrodominance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(new SkipDrawStepEffect()));

        // At the beginning of your end step, you may pay any amount of life. If you do, draw that many cards.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new NecrodominanceEffect(), TargetController.YOU, false
        ));

        // Your maximum hand size is five.
        this.addAbility(new SimpleStaticAbility(
                new MaximumHandSizeControllerEffect(
                        5, Duration.WhileOnBattlefield,
                        MaximumHandSizeControllerEffect.HandSizeModification.SET,
                        TargetController.YOU
                )
        ));

        // If a card or token would be put into your graveyard from anywhere, exile it instead.
        this.addAbility(new SimpleStaticAbility(new NecrodominanceReplacementEffect()));
    }

    private Necrodominance(final Necrodominance card) {
        super(card);
    }

    @Override
    public Necrodominance copy() {
        return new Necrodominance(this);
    }
}

// Inspired by Phyrexian Processor
class NecrodominanceEffect extends OneShotEffect {

    NecrodominanceEffect() {
        super(Outcome.LoseLife);
        staticText = "you may pay any amount of life. If you do, draw that many cards";
    }

    private NecrodominanceEffect(final NecrodominanceEffect effect) {
        super(effect);
    }

    @Override
    public NecrodominanceEffect copy() {
        return new NecrodominanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int payAmount = controller.getAmount(0, controller.getLife(), "Pay any amount of life", game);
        Cost cost = new PayLifeCost(payAmount);
        if (!cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        controller.drawCards(payAmount, source, game);
        return true;
    }

}

// Inspired by [Rest in Peace] and [Wheel of Sun and Moon]
class NecrodominanceReplacementEffect extends ReplacementEffectImpl {

    NecrodominanceReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a card or token would be put into your graveyard from anywhere, exile it instead";
    }

    private NecrodominanceReplacementEffect(final NecrodominanceReplacementEffect effect) {
        super(effect);
    }

    @Override
    public NecrodominanceReplacementEffect copy() {
        return new NecrodominanceReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() != Zone.GRAVEYARD) {
            return false;
        }
        Card card = game.getCard(event.getTargetId());
        if (card != null && card.isOwnedBy(source.getControllerId())) {
            return true;
        }
        Permanent token = game.getPermanent(event.getTargetId());
        if (token != null && token instanceof PermanentToken && token.isOwnedBy(source.getControllerId())) {
            return true;
        }
        return false;
    }
}
