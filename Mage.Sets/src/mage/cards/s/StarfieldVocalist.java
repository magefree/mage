package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StarfieldVocalist extends CardImpl {

    public StarfieldVocalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // If a permanent entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new StarfieldVocalistEffect()));

        // Warp {1}{U}
        this.addAbility(new WarpAbility(this, "{1}{U}"));
    }

    private StarfieldVocalist(final StarfieldVocalist card) {
        super(card);
    }

    @Override
    public StarfieldVocalist copy() {
        return new StarfieldVocalist(this);
    }
}

class StarfieldVocalistEffect extends ReplacementEffectImpl {

    StarfieldVocalistEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a permanent entering the battlefield causes a triggered ability of " +
                "a permanent you control to trigger, that ability triggers an additional time";
    }

    private StarfieldVocalistEffect(final StarfieldVocalistEffect effect) {
        super(effect);
    }

    @Override
    public StarfieldVocalistEffect copy() {
        return new StarfieldVocalistEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId())
                && Optional
                .ofNullable(event)
                .filter(NumberOfTriggersEvent.class::isInstance)
                .map(NumberOfTriggersEvent.class::cast)
                .map(NumberOfTriggersEvent::getSourceEvent)
                .filter(EntersTheBattlefieldEvent.class::isInstance)
                .isPresent()
                && game.getPermanent(event.getSourceId()) != null;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
