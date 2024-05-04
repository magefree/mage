package mage.cards.i;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class InnocentBystander extends CardImpl {

    public InnocentBystander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Innocent Bystander is dealt 3 or more damage, investigate.
        this.addAbility(new InnocentBystanderTriggeredAbility());

    }

    private InnocentBystander(final InnocentBystander card) {
        super(card);
    }

    @Override
    public InnocentBystander copy() {
        return new InnocentBystander(this);
    }
}

class InnocentBystanderTriggeredAbility extends TriggeredAbilityImpl {

    InnocentBystanderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new InvestigateEffect(), false);
        setTriggerPhrase("Whenever {this} is dealt 3 or more damage, ");
    }

    private InnocentBystanderTriggeredAbility(final InnocentBystanderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public InnocentBystanderTriggeredAbility copy() {
        return new InnocentBystanderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getSourceId()) && event.getAmount() >= 3;
    }
}
