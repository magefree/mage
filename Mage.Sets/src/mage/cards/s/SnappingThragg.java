
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class SnappingThragg extends CardImpl {

    public SnappingThragg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Snapping Thragg deals combat damage to a player, you may have it deal 3 damage to target creature that player controls.
        this.addAbility(new SnappingThraggTriggeredAbility());

        // Morph {4}{R}{R}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{4}{R}{R}")));

    }

    private SnappingThragg(final SnappingThragg card) {
        super(card);
    }

    @Override
    public SnappingThragg copy() {
        return new SnappingThragg(this);
    }
}

class SnappingThraggTriggeredAbility extends TriggeredAbilityImpl {

    public SnappingThraggTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(3), true);
        this.addTarget(new TargetCreaturePermanent());
    }

    public SnappingThraggTriggeredAbility(final SnappingThraggTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SnappingThraggTriggeredAbility copy() {
        return new SnappingThraggTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player opponent = game.getPlayer(event.getPlayerId());
        if (opponent != null && event.getSourceId().equals(this.sourceId)) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + opponent.getLogName() + " controls");
            filter.add(new ControllerIdPredicate(opponent.getId()));
            this.getTargets().clear();
            this.addTarget(new TargetCreaturePermanent(filter));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, you may have it deal 3 damage to target creature that player controls.";
    }
}
