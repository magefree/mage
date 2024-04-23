package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.LifeLostBatchEvent;
import mage.game.events.LifeLostEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author jimga150
 */
public final class ObNixilisCaptiveKingpin extends CardImpl {

    public ObNixilisCaptiveKingpin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever one or more opponents each lose exactly 1 life, put a +1/+1 counter on Ob Nixilis, Captive Kingpin. Exile the top card of your library. Until your next end step, you may play that card.
        Ability ability = new ObNixilisCaptiveKingpinAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        );
        ability.addEffect(new ExileTopXMayPlayUntilEffect(1, Duration.UntilYourNextEndStep)
                .withTextOptions("that card", false));

        this.addAbility(ability);

    }

    private ObNixilisCaptiveKingpin(final ObNixilisCaptiveKingpin card) {
        super(card);
    }

    @Override
    public ObNixilisCaptiveKingpin copy() {
        return new ObNixilisCaptiveKingpin(this);
    }
}

class ObNixilisCaptiveKingpinAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<LifeLostEvent> {

    ObNixilisCaptiveKingpinAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever one or more opponents each lose exactly 1 life, ");
    }

    private ObNixilisCaptiveKingpinAbility(final ObNixilisCaptiveKingpinAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_LIFE_BATCH;
    }

    @Override
    public Stream<LifeLostEvent> filterBatchEvent(GameEvent event, Game game) {
        LifeLostBatchEvent lifeLostBatchEvent = (LifeLostBatchEvent) event;
        Set<UUID> opponentsThatLost1LifeIds = new HashSet<>();
        for (UUID opponentId : game.getOpponents(getControllerId())) {
            if (1 == lifeLostBatchEvent.getLifeLostByPlayer(opponentId)) {
                opponentsThatLost1LifeIds.add(opponentId);
            }
        }
        return lifeLostBatchEvent
                .getEvents()
                .stream()
                .filter(e -> e.getAmount() > 0)
                .filter(e -> opponentsThatLost1LifeIds.contains(e.getTargetId()));
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return filterBatchEvent(event, game).findAny().isPresent();
    }

    @Override
    public ObNixilisCaptiveKingpinAbility copy() {
        return new ObNixilisCaptiveKingpinAbility(this);
    }
}
