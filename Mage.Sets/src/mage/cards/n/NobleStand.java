
package mage.cards.n;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
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

    public NobleStandAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(2));
    }

    public NobleStandAbility(final mage.cards.n.NobleStandAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
        filter.add(TokenPredicate.FALSE);
        Permanent permanent = game.getPermanent(event.getSourceId());
        return permanent != null && filter.match(permanent, controllerId, this, game);
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control blocks, you gain 2 life.";
    }

    @Override
    public mage.cards.n.NobleStandAbility copy() {
        return new mage.cards.n.NobleStandAbility(this);
    }
}
