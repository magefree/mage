
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33, Loki (Ashling the Extinguisher)
 */
public final class SparkMage extends CardImpl {

    public SparkMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Spark Mage deals combat damage to a player, you may have Spark Mage deal 1 damage to target creature that player controls.
        this.addAbility(new SparkMageTriggeredAbility());
    }

    private SparkMage(final SparkMage card) {
        super(card);
    }

    @Override
    public SparkMage copy() {
        return new SparkMage(this);
    }
}

class SparkMageTriggeredAbility extends TriggeredAbilityImpl {

    public SparkMageTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1));
        this.addTarget(new TargetCreaturePermanent());
    }

    private SparkMageTriggeredAbility(final SparkMageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SparkMageTriggeredAbility copy() {
        return new SparkMageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        if (damageEvent.isCombatDamage() && event.getSourceId().equals(this.getSourceId())) {
            Player opponent = game.getPlayer(event.getPlayerId());
            if (opponent != null) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + opponent.getLogName() + " controls");
                filter.add(new ControllerIdPredicate(opponent.getId()));
                this.getTargets().clear();
                this.getTargets().add(new TargetCreaturePermanent(filter));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, you may have {this} deal 1 damage to target creature that player controls.";
    }

}
