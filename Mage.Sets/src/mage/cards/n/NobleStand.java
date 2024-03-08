package mage.cards.n;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Galatolol
 */
public final class NobleStand extends CardImpl {

    public NobleStand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        // Whenever a creature you control blocks, you gain 2 life.
        this.addAbility(new NobleStandAbility());
    }

    private NobleStand(final NobleStand card) {
        super(card);
    }

    @Override
    public NobleStand copy() {
        return new NobleStand(this);
    }
}

class NobleStandAbility extends TriggeredAbilityImpl {

    NobleStandAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(2));
        setTriggerPhrase("Whenever a creature you control blocks, ");
    }

    private NobleStandAbility(final NobleStandAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        return permanent != null && StaticFilters.FILTER_CONTROLLED_CREATURE.match(permanent, controllerId, this, game);
    }

    @Override
    public NobleStandAbility copy() {
        return new NobleStandAbility(this);
    }
}
