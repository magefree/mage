package mage.cards.u;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class UpTheBeanstalk extends CardImpl {

    public UpTheBeanstalk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // When Up the Beanstalk enters the battlefield and whenever you cast a spell with mana value 5 or greater, draw a card.
        this.addAbility(new UpTheBeanstalkTriggeredAbility());
    }

    private UpTheBeanstalk(final UpTheBeanstalk card) {
        super(card);
    }

    @Override
    public UpTheBeanstalk copy() {
        return new UpTheBeanstalk(this);
    }
}

class UpTheBeanstalkTriggeredAbility extends TriggeredAbilityImpl {

    private static FilterSpell filter = new FilterSpell("spell with mana value 5 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 5));
    }

    UpTheBeanstalkTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        setTriggerPhrase("When {this} enters the battlefield and whenever you cast a spell with mana value 5 or greater, ");
    }

    private UpTheBeanstalkTriggeredAbility(final UpTheBeanstalkTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST
                || event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case SPELL_CAST:
                if (event.getPlayerId().equals(controllerId)) {
                    Spell spell = game.getStack().getSpell(event.getTargetId());
                    return spell != null && filter.match(spell, controllerId, this, game);
                }
                return false;
            case ENTERS_THE_BATTLEFIELD:
                return event.getTargetId().equals(getSourceId());
            default:
                return false;
        }
    }

    @Override
    public UpTheBeanstalkTriggeredAbility copy() {
        return new UpTheBeanstalkTriggeredAbility(this);
    }
}
