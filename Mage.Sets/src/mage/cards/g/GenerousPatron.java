package mage.cards.g;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.SupportAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
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

    private GenerousPatron(final GenerousPatron card) {
        super(card);
    }

    @Override
    public GenerousPatron copy() {
        return new GenerousPatron(this);
    }
}

class GenerousPatronTriggeredAbility extends TriggeredAbilityImpl {

    GenerousPatronTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    private GenerousPatronTriggeredAbility(GenerousPatronTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        return permanent != null
                && permanent.isCreature(game)
                && !permanent.isControlledBy(getControllerId());
    }

    @Override
    public GenerousPatronTriggeredAbility copy() {
        return new GenerousPatronTriggeredAbility(this);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever you put one or more counters on a creature you don't control, " ;
    }
}
