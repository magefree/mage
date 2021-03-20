
package mage.cards.f;

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
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class FlamebladeAngel extends CardImpl {

    public FlamebladeAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever a source an opponent controls deals damage to you or a permanent you control, you may have Flameblade Angel deal 1 damage to that source's controller.
        this.addAbility(new FlamebladeAngelTriggeredAbility());

    }

    private FlamebladeAngel(final FlamebladeAngel card) {
        super(card);
    }

    @Override
    public FlamebladeAngel copy() {
        return new FlamebladeAngel(this);
    }
}

class FlamebladeAngelTriggeredAbility extends TriggeredAbilityImpl {

    public FlamebladeAngelTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1), true);
    }

    public FlamebladeAngelTriggeredAbility(final FlamebladeAngelTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FlamebladeAngelTriggeredAbility copy() {
        return new FlamebladeAngelTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean result = false;
        UUID sourceControllerId = game.getControllerId(event.getSourceId());
        if (sourceControllerId != null && game.getOpponents(getControllerId()).contains(sourceControllerId)) {

            if (event.getTargetId().equals(getControllerId())) {
                result = true;
            } else {
                Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
                if (permanent != null && isControlledBy(permanent.getControllerId())) {
                    result = true;
                }
            }
            if (result) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(sourceControllerId));
                }
            }
        }
        return result;
    }

    @Override
    public String getRule() {
        return "Whenever a source an opponent controls deals damage to you or a permanent you control, you may have {this} deal 1 damage to that source's controller.";
    }
}
