
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetStackObject;

/**
 *
 * @author Styxo
 */
public final class JumpTrooper extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Trooper creatures");

    static {
        filter.add(SubType.TROOPER.getPredicate());
    }

    public JumpTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TROOPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a Trooper creature you control becomes the target of a spell or ability an opponent controls, counter that spell or abitlity unless its controller pays {2}.
        this.addAbility(new JumpTrooperTriggeredAbility(new CounterUnlessPaysEffect(new GenericManaCost(2))));

    }

    private JumpTrooper(final JumpTrooper card) {
        super(card);
    }

    @Override
    public JumpTrooper copy() {
        return new JumpTrooper(this);
    }
}

class JumpTrooperTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Trooper creature you control");

    static {
        filter.add(SubType.TROOPER.getPredicate());
    }

    public JumpTrooperTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public JumpTrooperTriggeredAbility(final JumpTrooperTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JumpTrooperTriggeredAbility copy() {
        return new JumpTrooperTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            Permanent creature = game.getPermanent(event.getTargetId());
            if (creature != null && filter.match(creature, getControllerId(), this, game)) {
                this.getTargets().clear();
                TargetStackObject target = new TargetStackObject();
                target.add(event.getSourceId(), game);
                this.addTarget(target);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Trooper creature you control becomes the target of a spell or ability an opponent controls, counter that spell or ability unless its controller pays {2}.";
    }
}
