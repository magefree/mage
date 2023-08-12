package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
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
 * @author Loki
 */
public final class AshlingTheExtinguisher extends CardImpl {

    public AshlingTheExtinguisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);

        // Whenever Ashling, the Extinguisher deals combat damage to a player, choose target creature that player controls. they sacrifice that creature.
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(new AshlingTheExtinguisherTriggeredAbility());
    }

    private AshlingTheExtinguisher(final AshlingTheExtinguisher card) {
        super(card);
    }

    @Override
    public AshlingTheExtinguisher copy() {
        return new AshlingTheExtinguisher(this);
    }

}

class AshlingTheExtinguisherTriggeredAbility extends TriggeredAbilityImpl {

    public AshlingTheExtinguisherTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeTargetEffect());
        this.addTarget(new TargetCreaturePermanent());
    }

    public AshlingTheExtinguisherTriggeredAbility(final AshlingTheExtinguisherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AshlingTheExtinguisherTriggeredAbility copy() {
        return new AshlingTheExtinguisherTriggeredAbility(this);
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
                this.addTarget(new TargetCreaturePermanent(filter));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, choose target creature that player controls. The player sacrifices that creature.";
    }
}
