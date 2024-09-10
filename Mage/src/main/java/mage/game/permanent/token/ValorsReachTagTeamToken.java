package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.PermanentToken;

/**
 * @author TheElk801
 */
public final class ValorsReachTagTeamToken extends TokenImpl {

    public ValorsReachTagTeamToken() {
        super("Warrior Token", "3/2 red and white Warrior creature token with \"Whenever this creature and at least one other creature token attack, put a +1/+1 counter on this creature.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WARRIOR);
        color.setWhite(true);
        color.setRed(true);
        power = new MageInt(3);
        toughness = new MageInt(2);

        this.addAbility(new ValorsReachTagTeamTokenTriggeredAbility());
    }

    private ValorsReachTagTeamToken(final ValorsReachTagTeamToken token) {
        super(token);
    }

    public ValorsReachTagTeamToken copy() {
        return new ValorsReachTagTeamToken(this);
    }
}

class ValorsReachTagTeamTokenTriggeredAbility extends TriggeredAbilityImpl {

    ValorsReachTagTeamTokenTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
    }

    private ValorsReachTagTeamTokenTriggeredAbility(final ValorsReachTagTeamTokenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ValorsReachTagTeamTokenTriggeredAbility copy() {
        return new ValorsReachTagTeamTokenTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game
                .getCombat()
                .getAttackers()
                .contains(getSourceId())
                && game
                .getCombat()
                .getAttackers()
                .stream()
                .filter(uuid -> !getSourceId().equals(uuid))
                .map(game::getPermanent)
                .anyMatch(PermanentToken.class::isInstance);
    }

    @Override
    public String getRule() {
        return "Whenever this creature and at least one other creature token attack, put a +1/+1 counter on this creature.";
    }
}
