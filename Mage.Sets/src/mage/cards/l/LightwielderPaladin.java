
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public final class LightwielderPaladin extends CardImpl {

    public LightwielderPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(new LightwielderPaladinTriggeredAbility());
    }

    private LightwielderPaladin(final LightwielderPaladin card) {
        super(card);
    }

    @Override
    public LightwielderPaladin copy() {
        return new LightwielderPaladin(this);
    }
}

class LightwielderPaladinTriggeredAbility extends TriggeredAbilityImpl {

    public LightwielderPaladinTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileTargetEffect(), true);
    }

    private LightwielderPaladinTriggeredAbility(final LightwielderPaladinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LightwielderPaladinTriggeredAbility copy() {
        return new LightwielderPaladinTriggeredAbility(this);
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
                FilterPermanent filter = new FilterPermanent("black or red permanent controlled by " + player.getLogName());
                filter.add(Predicates.or(
                    new ColorPredicate(ObjectColor.BLACK),
                    new ColorPredicate(ObjectColor.RED)));
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
        return "Whenever {this} deals combat damage to a player, you may exile target black or red permanent that player controls.";
    }
}
