
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author JayDi85
 */
public final class DinosaurHunter extends CardImpl {

    public DinosaurHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Dinosaur Hunter deals damage to a Dinosaur, destroy that creature.
        this.addAbility(new DinosaurHunterAbility());
    }

    public DinosaurHunter(final DinosaurHunter card) {
        super(card);
    }

    @Override
    public DinosaurHunter copy() {
        return new DinosaurHunter(this);
    }
}

class DinosaurHunterAbility extends TriggeredAbilityImpl {

    DinosaurHunterAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect());
    }

    DinosaurHunterAbility(final DinosaurHunterAbility ability) {
        super(ability);
    }

    @Override
    public DinosaurHunterAbility copy() {
        return new DinosaurHunterAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(getSourceId())) {
            Permanent targetPermanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (targetPermanent.hasSubtype(SubType.DINOSAUR, game)) {
                getEffects().setTargetPointer(new FixedTarget(targetPermanent, game));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage to a Dinosaur, destroy that creature.";
    }
}
