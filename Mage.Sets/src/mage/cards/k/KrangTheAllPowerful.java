package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DrawCardEvent;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.util.CardUtil;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class KrangTheAllPowerful extends CardImpl {

    public KrangTheAllPowerful(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.UTROM);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // If a player drawing a card causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new KrangTheAllPowerfulEffect()));

        // Whenever a player draws their second card each turn, put a +1/+1 counter on Krang.
        this.addAbility(new DrawNthCardTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            false, TargetController.ANY, 2
        ));

    }

    private KrangTheAllPowerful(final KrangTheAllPowerful card) {
        super(card);
    }

    @Override
    public KrangTheAllPowerful copy() {
        return new KrangTheAllPowerful(this);
    }
}

class KrangTheAllPowerfulEffect extends ReplacementEffectImpl {

    KrangTheAllPowerfulEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a player drawing a card causes a triggered ability " +
                "of a permanent you control to trigger, that ability triggers an additional time";
    }

    private KrangTheAllPowerfulEffect(final KrangTheAllPowerfulEffect effect) {
        super(effect);
    }

    @Override
    public KrangTheAllPowerfulEffect copy() {
        return new KrangTheAllPowerfulEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // Only triggers for the source controller
        if (!source.isControlledBy(event.getPlayerId())) {
            return false;
        }
        GameEvent sourceEvent = ((NumberOfTriggersEvent) event).getSourceEvent();
        // Only draw card triggers
        if (sourceEvent == null
            || sourceEvent.getType() != GameEvent.EventType.DRAW_CARD
            || !(sourceEvent instanceof DrawCardEvent)) {
            return false;
        }
        // Only for triggers of permanents
        return game.getPermanent(event.getSourceId()) != null;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }
}
