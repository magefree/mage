package mage.cards.o;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.*;
import mage.util.CardUtil;

/**
 *
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
    public boolean checkEvent(LifeLostEvent event, Game game) {
        return game.getOpponents(getControllerId()).contains(event.getTargetId());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        List<LifeLostEvent> filteredEvents = getFilteredEvents((LifeLostBatchEvent) event, game);
        if (filteredEvents.isEmpty()) {
            return false;
        }
        // if here, at least one opponent lost some amount of life
        return CardUtil.getEventTargets(event).stream()
                .allMatch(uuid -> LifeLostBatchEvent.getLifeLostByPlayer(filteredEvents, uuid) <= 1);
    }

    @Override
    public ObNixilisCaptiveKingpinAbility copy() {
        return new ObNixilisCaptiveKingpinAbility(this);
    }
}
