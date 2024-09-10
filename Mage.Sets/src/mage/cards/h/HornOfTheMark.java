package mage.cards.h;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.DefenderAttackedEvent;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HornOfTheMark extends CardImpl {

    public HornOfTheMark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);

        // Whenever two or more creatures you control attack a player, look at the top five cards of your library. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new HornOfTheMarkTriggeredAbility());
    }

    private HornOfTheMark(final HornOfTheMark card) {
        super(card);
    }

    @Override
    public HornOfTheMark copy() {
        return new HornOfTheMark(this);
    }
}

class HornOfTheMarkTriggeredAbility extends TriggeredAbilityImpl {

    HornOfTheMarkTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LookLibraryAndPickControllerEffect(
                5, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ));
        this.setTriggerPhrase("Whenever two or more creatures you control attack a player, ");
    }

    private HornOfTheMarkTriggeredAbility(final HornOfTheMarkTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HornOfTheMarkTriggeredAbility copy() {
        return new HornOfTheMarkTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DefenderAttackedEvent dEvent = ((DefenderAttackedEvent) event);
        return game.getPlayer(dEvent.getTargetId()) != null
                && dEvent
                .getAttackers(game)
                .stream()
                .map(Controllable::getControllerId)
                .filter(this::isControlledBy)
                .count() >= 2;
    }
}
