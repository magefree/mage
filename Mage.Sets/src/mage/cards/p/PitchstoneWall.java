
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class PitchstoneWall extends CardImpl {

    public PitchstoneWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Defender (This creature can't attack.)
        this.addAbility(DefenderAbility.getInstance());

        // Whenever you discard a card, you may sacrifice Pitchstone Wall. If you do, return the discarded card from your graveyard to your hand.
        this.addAbility(new PitchstoneWallTriggeredAbility());
    }

    private PitchstoneWall(final PitchstoneWall card) {
        super(card);
    }

    @Override
    public PitchstoneWall copy() {
        return new PitchstoneWall(this);
    }
}

class PitchstoneWallTriggeredAbility extends TriggeredAbilityImpl {

    public PitchstoneWallTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new ReturnToHandTargetEffect().setText("return the discarded card from your graveyard to your hand"), new SacrificeSourceCost()), false);
        setTriggerPhrase("Whenever you discard a card, ");
    }

    public PitchstoneWallTriggeredAbility(final PitchstoneWallTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PitchstoneWallTriggeredAbility copy() {
        return new PitchstoneWallTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(controllerId)) {
            Effect effect = this.getEffects().get(0);
            effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            return true;
        }
        return false;
    }
}
