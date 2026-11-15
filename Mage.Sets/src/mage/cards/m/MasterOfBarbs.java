package mage.cards.m;

import mage.MageInt;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchForPlayersEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class MasterOfBarbs extends CardImpl {

    public MasterOfBarbs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever one or more opponents are dealt noncombat damage, creatures you control get +1/+0 until end of turn.
        this.addAbility(new MasterOfBarbsTriggeredAbility());
    }

    private MasterOfBarbs(final MasterOfBarbs card) {
        super(card);
    }

    @Override
    public MasterOfBarbs copy() {
        return new MasterOfBarbs(this);
    }
}

class MasterOfBarbsTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPlayerEvent> {

    MasterOfBarbsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostControlledEffect(1, 0, Duration.EndOfTurn));
        setTriggerPhrase("Whenever one or more opponents are dealt noncombat damage, ");
    }

    private MasterOfBarbsTriggeredAbility(final MasterOfBarbsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MasterOfBarbsTriggeredAbility copy() {
        return new MasterOfBarbsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_PLAYERS;
    }

    @Override
    public boolean checkEvent(DamagedPlayerEvent event, Game game) {
        return !event.isCombatDamage()
                && game.getOpponents(getControllerId()).contains(event.getTargetId());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !getFilteredEvents((DamagedBatchForPlayersEvent) event, game).isEmpty();
    }
}
