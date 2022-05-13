
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class AtarkaWorldRender extends CardImpl {

    public AtarkaWorldRender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a Dragon you control attacks, it gains double strike until end of turn.
        this.addAbility(new AtarkaWorldRenderEffect());

    }

    private AtarkaWorldRender(final AtarkaWorldRender card) {
        super(card);
    }

    @Override
    public AtarkaWorldRender copy() {
        return new AtarkaWorldRender(this);
    }
}

class AtarkaWorldRenderEffect extends TriggeredAbilityImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("dragon you control");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public AtarkaWorldRenderEffect() {
        super(Zone.BATTLEFIELD, new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn));
    }

    public AtarkaWorldRenderEffect(final AtarkaWorldRenderEffect ability) {
        super(ability);
    }

    @Override
    public AtarkaWorldRenderEffect copy() {
        return new AtarkaWorldRenderEffect(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent attacker = game.getPermanent(event.getSourceId());
        if (attacker != null
                && filter.match(attacker, controllerId, this, game)) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(attacker.getId(), game));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Dragon you control attacks, it gains double strike until end of turn.";
    }

}
