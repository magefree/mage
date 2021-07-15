
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author tcontis
 */
public final class WoollySpider extends CardImpl {

    public WoollySpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // Whenever Woolly Spider blocks a creature with flying, Woolly Spider gets +0/+2 until end of turn.
        this.addAbility(new BlocksCreatureWithFlyingTriggeredAbility(new BoostSourceEffect(0, 2, Duration.EndOfTurn), false));
    }

    private WoollySpider(final WoollySpider card) {
        super(card);
    }

    @Override
    public WoollySpider copy() {
        return new WoollySpider(this);
    }
}

class BlocksCreatureWithFlyingTriggeredAbility extends TriggeredAbilityImpl {

    public BlocksCreatureWithFlyingTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public BlocksCreatureWithFlyingTriggeredAbility(final BlocksCreatureWithFlyingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.getSourceId()) && game.getPermanent(event.getTargetId()).getAbilities().containsKey(FlyingAbility.getInstance().getId());
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} blocks a creature with flying, " ;
    }

    @Override
    public BlocksCreatureWithFlyingTriggeredAbility copy() {
        return new BlocksCreatureWithFlyingTriggeredAbility(this);
    }
}