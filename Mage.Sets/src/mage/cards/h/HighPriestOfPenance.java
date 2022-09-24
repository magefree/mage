package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author Plopman
 */
public final class HighPriestOfPenance extends CardImpl {

    public HighPriestOfPenance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Whenever High Priest of Penance is dealt damage, you may destroy target nonland permanent.
        this.addAbility(new HighPriestOfPenanceTriggeredAbility());
    }

    private HighPriestOfPenance(final HighPriestOfPenance card) {
        super(card);
    }

    @Override
    public HighPriestOfPenance copy() {
        return new HighPriestOfPenance(this);
    }
}

class HighPriestOfPenanceTriggeredAbility extends TriggeredAbilityImpl {

    public HighPriestOfPenanceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), true);
        this.addTarget(new TargetNonlandPermanent());
        setTriggerPhrase("Whenever {this} is dealt damage, ");
    }

    public HighPriestOfPenanceTriggeredAbility(final HighPriestOfPenanceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HighPriestOfPenanceTriggeredAbility copy() {
        return new HighPriestOfPenanceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(this.sourceId);
    }
}
