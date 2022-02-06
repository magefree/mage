
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.NinjutsuAbility;
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
 * @author LevelX2
 */
public final class MistbladeShinobi extends CardImpl {

    public MistbladeShinobi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Ninjutsu {1}{U} ({1}{U}, Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)
        this.addAbility(new NinjutsuAbility("{U}"));

        // Whenever Mistblade Shinobi deals combat damage to a player, you may return target creature that player controls to its owner's hand.
        this.addAbility(new MistbladeShinobiTriggeredAbility());
    }

    private MistbladeShinobi(final MistbladeShinobi card) {
        super(card);
    }

    @Override
    public MistbladeShinobi copy() {
        return new MistbladeShinobi(this);
    }
}

class MistbladeShinobiTriggeredAbility extends TriggeredAbilityImpl {

    MistbladeShinobiTriggeredAbility() {
        super(Zone.BATTLEFIELD,  new ReturnToHandTargetEffect(), true);
    }

    MistbladeShinobiTriggeredAbility(final MistbladeShinobiTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MistbladeShinobiTriggeredAbility copy() {
        return new MistbladeShinobiTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()
                && event.getSourceId().equals(sourceId)) {
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
        return "Whenever {this} deals combat damage to a player, you may return target creature that player controls to its owner's hand.";
    }
}