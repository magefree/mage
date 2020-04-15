
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public final class Standstill extends CardImpl {

    public Standstill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");


        // When a player casts a spell, sacrifice Standstill. If you do, each of that player's opponents draws three cards.
        this.addAbility(new SpellCastTriggeredAbility());
    }

    public Standstill(final Standstill card) {
        super(card);
    }

    @Override
    public Standstill copy() {
        return new Standstill(this);
    }
}

class SpellCastTriggeredAbility extends TriggeredAbilityImpl {

    public SpellCastTriggeredAbility() {
        super(Zone.BATTLEFIELD, new StandstillEffect(), false);
    }

    public SpellCastTriggeredAbility(final SpellCastTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
        return true;
    }

    @Override
    public String getRule() {
        return "When a player casts a spell, sacrifice Standstill. If you do, each of that player's opponents draws three cards.";
    }

    @Override
    public SpellCastTriggeredAbility copy() {
        return new SpellCastTriggeredAbility(this);
    }
}

class StandstillEffect extends OneShotEffect {

    public StandstillEffect() {
        super(Outcome.Sacrifice);
        staticText = "sacrifice {this}. If you do, each of that player's opponents draws three cards";
    }

    public StandstillEffect(final StandstillEffect effect) {
        super(effect);
    }

    @Override
    public StandstillEffect copy() {
        return new StandstillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            if (permanent.sacrifice(source.getSourceId(), game)) {
                for (UUID uuid : game.getOpponents(this.getTargetPointer().getFirst(game, source))) {
                    Player player = game.getPlayer(uuid);
                    if (player != null) {
                        player.drawCards(3, source.getSourceId(), game);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
