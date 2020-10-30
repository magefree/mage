package mage.cards.p;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PairedTactician extends CardImpl {

    public PairedTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Paired Tactician and at least one other Warrior attack, put a +1/+1 counter on Paired Tactician.
        this.addAbility(new PairedTacticianTriggeredAbility());
    }

    private PairedTactician(final PairedTactician card) {
        super(card);
    }

    @Override
    public PairedTactician copy() {
        return new PairedTactician(this);
    }
}

class PairedTacticianTriggeredAbility extends TriggeredAbilityImpl {

    PairedTacticianTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
    }

    private PairedTacticianTriggeredAbility(final PairedTacticianTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PairedTacticianTriggeredAbility copy() {
        return new PairedTacticianTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat().getAttackers().contains(this.sourceId)
                && game
                .getCombat()
                .getAttackers()
                .stream()
                .filter(uuid -> !this.getSourceId().equals(uuid))
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> permanent.hasSubtype(SubType.WARRIOR, game));
    }

    @Override
    public String getRule() {
        return "Whenever {this} and at least one other Warrior attack, put a +1/+1 counter on {this}.";
    }
}
