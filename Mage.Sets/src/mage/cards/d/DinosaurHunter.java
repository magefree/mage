package mage.cards.d;

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
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

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

    private DinosaurHunter(final DinosaurHunter card) {
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

    private DinosaurHunterAbility(final DinosaurHunterAbility ability) {
        super(ability);
    }

    @Override
    public DinosaurHunterAbility copy() {
        return new DinosaurHunterAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(getSourceId())) {
            return false;
        }
        Permanent targetPermanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (targetPermanent == null || !targetPermanent.hasSubtype(SubType.DINOSAUR, game)) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(targetPermanent, game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage to a Dinosaur, destroy that creature.";
    }
}
