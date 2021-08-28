
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class ThunderbreakRegent extends CardImpl {

    public ThunderbreakRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a Dragon you control becomes the target of a spell or ability your opponent controls, Thunderbreak Regent deals 3 damage to that player.
        this.addAbility(new ThunderbreakRegentTriggeredAbility(new DamageTargetEffect(3)));
    }

    private ThunderbreakRegent(final ThunderbreakRegent card) {
        super(card);
    }

    @Override
    public ThunderbreakRegent copy() {
        return new ThunderbreakRegent(this);
    }
}

class ThunderbreakRegentTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Sliver creature you control");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public ThunderbreakRegentTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public ThunderbreakRegentTriggeredAbility(final ThunderbreakRegentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThunderbreakRegentTriggeredAbility copy() {
        return new ThunderbreakRegentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            Permanent creature = game.getPermanent(event.getTargetId());
            if (creature != null && filter.match(creature, getSourceId(), getControllerId(), game)) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Dragon you control becomes the target of a spell or ability an opponent controls, {this} deals 3 damage to that player.";
    }
}
