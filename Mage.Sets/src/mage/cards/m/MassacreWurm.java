package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Loki
 */
public final class MassacreWurm extends CardImpl {

    public MassacreWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostOpponentsEffect(-2, -2, Duration.EndOfTurn)));
        this.addAbility(new MassacreWurmTriggeredAbility());
    }

    private MassacreWurm(final MassacreWurm card) {
        super(card);
    }

    @Override
    public MassacreWurm copy() {
        return new MassacreWurm(this);
    }

}

class MassacreWurmTriggeredAbility extends TriggeredAbilityImpl {

    MassacreWurmTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(2));
        setTriggerPhrase("Whenever a creature an opponent controls dies, ");
    }

    MassacreWurmTriggeredAbility(final MassacreWurmTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MassacreWurmTriggeredAbility copy() {
        return new MassacreWurmTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).isDiesEvent()) {
            Permanent p = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (p != null && p.isCreature(game) && game.getOpponents(this.getControllerId()).contains(p.getControllerId())) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(p.getControllerId()));
                }
                return true;
            }
        }
        return false;
    }
}
