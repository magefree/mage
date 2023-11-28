package mage.cards.c;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.abilities.hint.StaticHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CuratorOfSunsCreation extends CardImpl {

    public CuratorOfSunsCreation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you discover, discover again for the same value. This ability triggers only once per turn.
        this.addAbility(new CuratorOfSunsCreationTriggeredAbility());
    }

    private CuratorOfSunsCreation(final CuratorOfSunsCreation card) {
        super(card);
    }

    @Override
    public CuratorOfSunsCreation copy() {
        return new CuratorOfSunsCreation(this);
    }
}

class CuratorOfSunsCreationTriggeredAbility extends TriggeredAbilityImpl {

    CuratorOfSunsCreationTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
        setTriggersOnceEachTurn(true);
    }

    private CuratorOfSunsCreationTriggeredAbility(final CuratorOfSunsCreationTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CuratorOfSunsCreationTriggeredAbility copy() {
        return new CuratorOfSunsCreationTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCOVERED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId())) {
            return false;
        }

        int amount = event.getAmount();
        this.getEffects().clear();
        this.getEffects().add(new DiscoverEffect(amount));
        this.getHints().clear();
        this.getHints().add(new StaticHint("Discover amount: " + amount));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you discover, discover again for the same value. This ability triggers only once each turn.";
    }
}
