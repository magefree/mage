package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuantumRiddler extends CardImpl {

    public QuantumRiddler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // As long as you have one or fewer cards in hand, if you would draw one or more cards, you draw that many cards plus one instead.
        this.addAbility(new SimpleStaticAbility(new QuantumRiddlerReplacementEffect()));

        // Warp {1}{U}
        this.addAbility(new WarpAbility(this, "{1}{U}"));
    }

    private QuantumRiddler(final QuantumRiddler card) {
        super(card);
    }

    @Override
    public QuantumRiddler copy() {
        return new QuantumRiddler(this);
    }
}

class QuantumRiddlerReplacementEffect extends ReplacementEffectImpl {

    QuantumRiddlerReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "as long as you have one or fewer cards in hand, " +
                "if you would draw one or more cards, you draw that many cards plus one instead";
    }

    private QuantumRiddlerReplacementEffect(final QuantumRiddlerReplacementEffect effect) {
        super(effect);
    }

    @Override
    public QuantumRiddlerReplacementEffect copy() {
        return new QuantumRiddlerReplacementEffect(this);
    }

    // While this card technically replaces a multi-draw event, it's effectively the same as replacing a single draw
    // as the first replacement will immediately negate the future replacements from occurring
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!source.isControlledBy(event.getPlayerId())) {
            return false;
        }
        return source.isControlledBy(event.getPlayerId())
                && Optional
                .ofNullable(event)
                .map(GameEvent::getPlayerId)
                .map(game::getPlayer)
                .map(Player::getHand)
                .map(Set::size)
                .filter(x -> x <= 1)
                .isPresent();
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Optional.ofNullable(event)
                .map(GameEvent::getPlayerId)
                .map(game::getPlayer)
                .ifPresent(player -> player.drawCards(2, source, game, event));
        return true;
    }
}
