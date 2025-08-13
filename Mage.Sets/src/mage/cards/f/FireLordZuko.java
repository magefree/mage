package mage.cards.f;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireLordZuko extends CardImpl {

    public FireLordZuko(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Firebending X, where X is Fire Lord Zuko's power.
        this.addAbility(new FirebendingAbility(SourcePermanentPowerValue.NOT_NEGATIVE));

        // Whenever you cast a spell from exile and whenever a permanent you control enters from exile, put a +1/+1 counter on each creature you control.
        this.addAbility(new FireLordZukoTriggeredAbility());
    }

    private FireLordZuko(final FireLordZuko card) {
        super(card);
    }

    @Override
    public FireLordZuko copy() {
        return new FireLordZuko(this);
    }
}

class FireLordZukoTriggeredAbility extends TriggeredAbilityImpl {

    FireLordZukoTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE));
        setTriggerPhrase("Whenever you cast a spell from exile and whenever a permanent you control enters from exile, ");
    }

    private FireLordZukoTriggeredAbility(final FireLordZukoTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FireLordZukoTriggeredAbility copy() {
        return new FireLordZukoTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!this.isControlledBy(event.getPlayerId())) {
            return false;
        }
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
                return Zone.EXILED.match(((EntersTheBattlefieldEvent) event).getFromZone());
            case SPELL_CAST:
                return Optional
                        .ofNullable(game.getSpell(event.getTargetId()))
                        .map(Spell::getFromZone)
                        .filter(Zone.EXILED::match)
                        .isPresent();
            default:
                return false;
        }
    }
}
