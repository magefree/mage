package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DefenderAttackedEvent;
import mage.game.events.GameEvent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RigoStreetwiseMentor extends CardImpl {

    public RigoStreetwiseMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G/W}{W}{W/U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Rigo, Streetwise Mentor enters the battlefield with a shield counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.SHIELD.createInstance(1)),
                "with a shield counter on it. <i>(If it would be dealt damage " +
                        "or destroyed, remove a shield counter from it instead.)</i>"
        ));

        // Whenever you attack a player or planeswalker with one or more creatures with power 1 or less, draw a card.
        this.addAbility(new RigoStreetwiseMentorTriggeredAbility());
    }

    private RigoStreetwiseMentor(final RigoStreetwiseMentor card) {
        super(card);
    }

    @Override
    public RigoStreetwiseMentor copy() {
        return new RigoStreetwiseMentor(this);
    }
}

class RigoStreetwiseMentorTriggeredAbility extends TriggeredAbilityImpl {

    RigoStreetwiseMentorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    private RigoStreetwiseMentorTriggeredAbility(final RigoStreetwiseMentorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RigoStreetwiseMentorTriggeredAbility copy() {
        return new RigoStreetwiseMentorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId())
                && ((DefenderAttackedEvent) event)
                .getAttackers(game)
                .stream()
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .anyMatch(x -> x <= 1);
    }

    @Override
    public String getRule() {
        return "Whenever you attack a player or planeswalker with one or more creatures with power 1 or less, draw a card.";
    }
}
