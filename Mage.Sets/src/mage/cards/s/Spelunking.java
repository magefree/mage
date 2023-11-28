package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class Spelunking extends CardImpl {

    public Spelunking(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // When Spelunking enters the battlefield, draw a card, then you may put a land card from your hand onto the battlefield. If you put a Cave onto the battlefield this way, you gain 4 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SpelunkingEffect()));

        // Lands you control enter the battlefield untapped.
        this.addAbility(new SimpleStaticAbility(new SpelunkingReplacementEffect()));
    }

    private Spelunking(final Spelunking card) {
        super(card);
    }

    @Override
    public Spelunking copy() {
        return new Spelunking(this);
    }
}

class SpelunkingEffect extends OneShotEffect {

    SpelunkingEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card, then you may put a land card from your hand onto the battlefield. "
                + "If you put a Cave onto the battlefield this way, you gain 4 life";
    }

    private SpelunkingEffect(final SpelunkingEffect effect) {
        super(effect);
    }

    @Override
    public SpelunkingEffect copy() {
        return new SpelunkingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // draw a card
        controller.drawCards(1, source, game);

        // you may put a land card from your hand onto the battlefield
        TargetCardInHand target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_LAND);
        if (controller.choose(Outcome.PutLandInPlay, target, source, game)) {
            Card landInHand = game.getCard(target.getFirstTarget());
            if (landInHand != null) {
                controller.moveCards(landInHand, Zone.BATTLEFIELD, source, game);
                if (landInHand.getSubtype(game).contains(SubType.CAVE)) {
                    // If you put a Cave onto the battlefield this way, you gain 4 life
                    controller.gainLife(4, game, source);
                }
            }
        }

        return true;
    }

}

/**
 * Inspired by {@link mage.cards.g.GondGate}
 */
class SpelunkingReplacementEffect extends ReplacementEffectImpl {

    SpelunkingReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Lands you control enter the battlefield untapped";
    }

    private SpelunkingReplacementEffect(final SpelunkingReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.setTapped(false);
        }

        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject == null) {
            return false;
        }

        Permanent targetObject = ((EntersTheBattlefieldEvent) event).getTarget();
        if (targetObject == null) {
            return false;
        }

        return !sourceObject.getId().equals(targetObject.getId())
                && targetObject.isControlledBy(source.getControllerId());
    }

    @Override
    public SpelunkingReplacementEffect copy() {
        return new SpelunkingReplacementEffect(this);
    }
}
