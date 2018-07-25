package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;

/**
 *
 * @author NinthWorld
 */
public final class Ghost extends CardImpl {

    public Ghost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.TERRAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Morph {W}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{W}")));

        // Whenever Ghost deals combat damage to a player, you may destroy target artifact or enchantment that player controls.
        this.addAbility(new GhostTriggeredAbility());
    }

    public Ghost(final Ghost card) {
        super(card);
    }

    @Override
    public Ghost copy() {
        return new Ghost(this);
    }
}

class GhostTriggeredAbility extends TriggeredAbilityImpl {

    GhostTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), true);
    }

    GhostTriggeredAbility(final GhostTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GhostTriggeredAbility copy() {
        return new GhostTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        if (damageEvent.isCombatDamage() && event.getSourceId().equals(this.getSourceId())) {
            FilterPermanent filter = new FilterPermanent("artifact or enchantment that player controls");
            filter.add(new CardTypePredicate(CardType.ARTIFACT));
            filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
            filter.add(new ControllerIdPredicate(event.getPlayerId()));
            filter.setMessage("artifact or enchantment controlled by " + game.getPlayer(event.getTargetId()).getLogName());

            this.getTargets().clear();
            this.addTarget(new TargetPermanent(filter));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, you may destroy target artifact or enchantment that player controls.";
    }
}