
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedCreatureEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author nantuko
 */
public final class CreepyDoll extends CardImpl {

    public CreepyDoll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Creepy Doll is indestructible.
        this.addAbility(IndestructibleAbility.getInstance());

        // Whenever Creepy Doll deals combat damage to a creature, flip a coin. If you win the flip, destroy that creature.
        this.addAbility(new CreepyDollTriggeredAbility());
    }

    public CreepyDoll(final CreepyDoll card) {
        super(card);
    }

    @Override
    public CreepyDoll copy() {
        return new CreepyDoll(this);
    }
}

class CreepyDollTriggeredAbility extends TriggeredAbilityImpl {

    CreepyDollTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreepyDollEffect());
    }

    CreepyDollTriggeredAbility(final CreepyDollTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CreepyDollTriggeredAbility copy() {
        return new CreepyDollTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedCreatureEvent) event).isCombatDamage() && event.getSourceId().equals(sourceId)) {
            getEffects().get(0).setTargetPointer(new FixedTarget(event.getTargetId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a creature, flip a coin. If you win the flip, destroy that creature.";
    }
}

class CreepyDollEffect extends OneShotEffect {

    CreepyDollEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "";
    }

    CreepyDollEffect(final CreepyDollEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (player.flipCoin(game)) {
                UUID targetId = getTargetPointer().getFirst(game, source);
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    permanent.destroy(source.getSourceId(), game, false);
                }
            }
        }
        return false;
    }

    @Override
    public CreepyDollEffect copy() {
        return new CreepyDollEffect(this);
    }
}
