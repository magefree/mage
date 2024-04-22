package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class CreepyDoll extends CardImpl {

    public CreepyDoll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Creepy Doll is indestructible.
        this.addAbility(IndestructibleAbility.getInstance());

        // Whenever Creepy Doll deals combat damage to a creature, flip a coin. If you win the flip, destroy that creature.
        this.addAbility(new CreepyDollTriggeredAbility());
    }

    private CreepyDoll(final CreepyDoll card) {
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

    private CreepyDollTriggeredAbility(final CreepyDollTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CreepyDollTriggeredAbility copy() {
        return new CreepyDollTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null
                && permanent.isCreature(game)
                && ((DamagedEvent) event).isCombatDamage()
                && event.getSourceId().equals(sourceId)) {
            getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
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

    private CreepyDollEffect(final CreepyDollEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (player.flipCoin(source, game, true)) {
                UUID targetId = getTargetPointer().getFirst(game, source);
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    permanent.destroy(source, game, false);
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
