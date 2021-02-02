
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetStackObject;

/**
 *
 * @author LevelX2
 */
public final class ShimmeringGlasskite extends CardImpl {

    public ShimmeringGlasskite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.SPIRIT);


        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Shimmering Glasskite becomes the target of a spell or ability for the first time each turn, counter that spell or ability.
        this.addAbility(new ShimmeringGlasskiteAbility());

    }

    private ShimmeringGlasskite(final ShimmeringGlasskite card) {
        super(card);
    }

    @Override
    public ShimmeringGlasskite copy() {
        return new ShimmeringGlasskite(this);
    }
}

class ShimmeringGlasskiteAbility extends TriggeredAbilityImpl {

    protected int turnUsed;

    public ShimmeringGlasskiteAbility() {
        super(Zone.BATTLEFIELD, new CounterTargetEffect(), false);
    }

    public ShimmeringGlasskiteAbility(final ShimmeringGlasskiteAbility ability) {
        super(ability);
        turnUsed = ability.turnUsed;
    }

    @Override
    public ShimmeringGlasskiteAbility copy() {
        return new ShimmeringGlasskiteAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId()) && game.getTurnNum() > turnUsed) {
            this.getTargets().clear();
            TargetStackObject target = new TargetStackObject();
            target.add(event.getSourceId(), game);
            this.addTarget(target);
            turnUsed = game.getTurnNum();
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes the target of a spell or ability for the first time each turn, counter that spell or ability.";
    }

}
