
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class SludgeStrider extends CardImpl {

    public SludgeStrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}{W}{U}{B}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another artifact enters the battlefield under your control or another artifact you control leaves the battlefield, you may pay {1}. If you do, target player loses 1 life and you gain 1 life.
        Ability ability = new SludgeStriderTriggeredAbility();
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    public SludgeStrider(final SludgeStrider card) {
        super(card);
    }

    @Override
    public SludgeStrider copy() {
        return new SludgeStrider(this);
    }
}

class SludgeStriderTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterArtifactCard filter = new FilterArtifactCard("another artifact under your control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(AnotherPredicate.instance);
    }

    public SludgeStriderTriggeredAbility() {
        // setting optional = false because DoIfCostPaid already asks the player
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new SludgeStriderEffect(), new GenericManaCost(1)), false);
    }

    public SludgeStriderTriggeredAbility(final SludgeStriderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD || event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && filter.match(permanent, getSourceId(), getControllerId(), game)) {
                return true;
            }
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                UUID targetId = event.getTargetId();
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    permanent = (Permanent) game.getLastKnownInformation(targetId, Zone.BATTLEFIELD);
                }
                if (permanent != null && filter.match(permanent, getSourceId(), getControllerId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public SludgeStriderTriggeredAbility copy() {
        return new SludgeStriderTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever another artifact enters the battlefield under your control or another artifact you control leaves the battlefield, you may pay {1}. If you do, target player loses 1 life and you gain 1 life.";
    }
}

class SludgeStriderEffect extends OneShotEffect {

    SludgeStriderEffect() {
        super(Outcome.Detriment);
        staticText = "target player loses 1 life and you gain 1 life";
    }

    SludgeStriderEffect(final SludgeStriderEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (targetPlayer != null) {
            targetPlayer.loseLife(1, game, false);
        }
        if (you != null) {
            you.gainLife(1, game, source);
        }
        return true;
    }

    @Override
    public SludgeStriderEffect copy() {
        return new SludgeStriderEffect(this);
    }
}
