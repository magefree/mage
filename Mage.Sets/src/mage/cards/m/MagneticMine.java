
package mage.cards.m;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class MagneticMine extends CardImpl {

    public MagneticMine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Whenever another artifact is put into a graveyard from the battlefield, Magnetic Mine deals 2 damage to that artifact’s controller.
        MagneticMineTriggeredAbility ability = new MagneticMineTriggeredAbility(new DamageTargetEffect(2));
        ability.addTarget(new TargetPlayer().withNotTarget(true));
        this.addAbility(ability);
    }

    private MagneticMine(final MagneticMine card) {
        super(card);
    }

    @Override
    public MagneticMine copy() {
        return new MagneticMine(this);
    }
}

class MagneticMineTriggeredAbility extends TriggeredAbilityImpl {

    public MagneticMineTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    private MagneticMineTriggeredAbility(final MagneticMineTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()
                && zEvent.getTarget().isArtifact(game)
                && !Objects.equals(zEvent.getTarget().getId(), this.getSourceId())) {
            this.getTargets().get(0).add(zEvent.getTarget().getControllerId(), game);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever another artifact is put into a graveyard from the battlefield, {this} deals 2 damage to that artifact's controller";
    }

    @Override
    public MagneticMineTriggeredAbility copy() {
        return new MagneticMineTriggeredAbility(this);
    }
}