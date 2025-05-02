package mage.cards.i;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.BloodToken;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class IvoraInsatiableHeir extends CardImpl {

    public IvoraInsatiableHeir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Ivora, Insatiable Heir enters and whenever it deals combat damage to a player, create a Blood token.
        this.addAbility(new IvoraInsatiableHeirTriggeredAbility());

        // Whenever you discard a card, put a +1/+1 counter on Ivora.
        this.addAbility(new DiscardCardControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));
    }

    private IvoraInsatiableHeir(final IvoraInsatiableHeir card) {
        super(card);
    }

    @Override
    public IvoraInsatiableHeir copy() {
        return new IvoraInsatiableHeir(this);
    }
}

class IvoraInsatiableHeirTriggeredAbility extends TriggeredAbilityImpl {

    IvoraInsatiableHeirTriggeredAbility() {
        super(Zone.ALL, new CreateTokenEffect(new BloodToken()), false);
        setTriggerPhrase("When {this} enters and whenever it deals combat damage to a player, ");
    }

    private IvoraInsatiableHeirTriggeredAbility(final IvoraInsatiableHeirTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public IvoraInsatiableHeirTriggeredAbility copy() {
        return new IvoraInsatiableHeirTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
            case DAMAGED_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
                return event.getTargetId().equals(getSourceId());
            case DAMAGED_PLAYER:
                return event.getSourceId().equals(getSourceId()) && ((DamagedEvent) event).isCombatDamage();
            default:
                return false;
        }
    }

}
