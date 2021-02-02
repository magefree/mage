
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LoneFox

 */
public final class LavaRunner extends CardImpl {

    public LavaRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Whenever Lava Runner becomes the target of a spell or ability, that spell or ability's controller sacrifices a land.
        this.addAbility(new LavaRunnerAbility());
    }

    private LavaRunner(final LavaRunner card) {
        super(card);
    }

    @Override
    public LavaRunner copy() {
        return new LavaRunner(this);
    }
}

class LavaRunnerAbility extends TriggeredAbilityImpl {

    public LavaRunnerAbility() {
        super(Zone.BATTLEFIELD, new SacrificeEffect(new FilterLandPermanent(), 1, ""), false);
    }

    public LavaRunnerAbility(final LavaRunnerAbility ability) {
        super(ability);
    }

    @Override
    public LavaRunnerAbility copy() {
        return new LavaRunnerAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if(event.getTargetId().equals(this.getSourceId())) {
            getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes the target of a spell or ability, that spell or ability's controller sacrifices a land.";
    }
}
