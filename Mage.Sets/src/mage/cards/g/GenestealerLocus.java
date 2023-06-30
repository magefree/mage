package mage.cards.g;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttackedByCreatureTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class GenestealerLocus extends CardImpl {

    public GenestealerLocus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.TYRANID, SubType.HUMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Neurotraumal Rod — Whenever a creature attacks you, it gets -1/-0 until end of turn.
        this.addAbility(new AttackedByCreatureTriggeredAbility(
                new BoostTargetEffect(-1, 0, Duration.EndOfTurn), false, SetTargetPointer.PERMANENT)
                .withFlavorWord("Neurotraumal Rod"));

        // Whenever a creature attacks one of your opponents, it gets +0/+1 until end of turn.
        this.addAbility(new GenestealerLocusTriggeredAbility());
    }

    private GenestealerLocus(final GenestealerLocus card) {
        super(card);
    }

    @Override
    public GenestealerLocus copy() {
        return new GenestealerLocus(this);
    }
}

class GenestealerLocusTriggeredAbility extends TriggeredAbilityImpl {

    public GenestealerLocusTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(0, 1, Duration.EndOfTurn), false);
    }

    public GenestealerLocusTriggeredAbility(final GenestealerLocusTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.getControllerId()).contains(event.getTargetId())) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getSourceId(), game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature attacks one of your opponents, it gets +0/+1 until end of turn.";
    }

    @Override
    public GenestealerLocusTriggeredAbility copy() {
        return new GenestealerLocusTriggeredAbility(this);
    }
}
