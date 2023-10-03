
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class TrygonPredator extends CardImpl {

    public TrygonPredator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{U}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Trygon Predator deals combat damage to a player, you may destroy target artifact or enchantment that player controls.
        this.addAbility(new TrygonPredatorTriggeredAbility());
    }

    private TrygonPredator(final TrygonPredator card) {
        super(card);
    }

    @Override
    public TrygonPredator copy() {
        return new TrygonPredator(this);
    }
}

class TrygonPredatorTriggeredAbility extends TriggeredAbilityImpl {

    public TrygonPredatorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), true);
    }

    private TrygonPredatorTriggeredAbility(final TrygonPredatorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TrygonPredatorTriggeredAbility copy() {
        return new TrygonPredatorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.sourceId) && ((DamagedPlayerEvent) event).isCombatDamage()) {
            Player player = game.getPlayer(event.getTargetId());
            if (player != null) {
                FilterPermanent filter = new FilterPermanent("an artifact or enchantment controlled by " + player.getLogName());
                filter.add(Predicates.or(
                    CardType.ARTIFACT.getPredicate(),
                    CardType.ENCHANTMENT.getPredicate()));
                filter.add(new ControllerIdPredicate(event.getTargetId()));

                this.getTargets().clear();
                this.addTarget(new TargetPermanent(filter));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, you may destroy target artifact or enchantment that player controls.";
    }
}
