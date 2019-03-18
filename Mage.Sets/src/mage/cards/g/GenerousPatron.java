package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.SupportAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class GenerousPatron extends CardImpl {

    public GenerousPatron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Generous Patron enters the battlefield, support 2. (Put a +1/+1 counter on each of up to two other target creatures.)
        this.addAbility(new SupportAbility(this, 2));

        // Whenever you put one or more counters on a creature you don't control, draw a card.
        this.addAbility(new GenerousPatronTriggeredAbility());
    }

    public GenerousPatron(final GenerousPatron card) {
        super(card);
    }

    @Override
    public GenerousPatron copy() {
        return new GenerousPatron(this);
    }
}

class GenerousPatronTriggeredAbility extends TriggeredAbilityImpl {

    public GenerousPatronTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    public GenerousPatronTriggeredAbility(GenerousPatronTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(game.getControllerId(event.getSourceId()))) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        return permanent != null
                && permanent.isCreature()
                && !permanent.isControlledBy(getControllerId());
    }

    @Override
    public GenerousPatronTriggeredAbility copy() {
        return new GenerousPatronTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you put one or more counters on a creature you don't control, " + super.getRule();
    }
}
