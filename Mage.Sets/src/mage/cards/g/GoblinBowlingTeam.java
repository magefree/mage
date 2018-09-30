
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author L_J
 */
public final class GoblinBowlingTeam extends CardImpl {

    public GoblinBowlingTeam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // If Goblin Bowling Team would deal damage to a permanent or player, it deals that much damage plus the result of a six-sided die roll to that permanent or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GoblinBowlingTeamEffect()));

    }

    public GoblinBowlingTeam(final GoblinBowlingTeam card) {
        super(card);
    }

    @Override
    public GoblinBowlingTeam copy() {
        return new GoblinBowlingTeam(this);
    }
}

class GoblinBowlingTeamEffect extends ReplacementEffectImpl {

    public GoblinBowlingTeamEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If {this} would deal damage to a permanent or player, it deals that much damage plus the result of a six-sided die roll to that permanent or player instead";
    }

    public GoblinBowlingTeamEffect(final GoblinBowlingTeamEffect effect) {
        super(effect);
    }

    @Override
    public GoblinBowlingTeamEffect copy() {
        return new GoblinBowlingTeamEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_CREATURE:
            case DAMAGE_PLAYER:
            case DAMAGE_PLANESWALKER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getSourceId().equals(source.getSourceId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            DamageEvent damageEvent = (DamageEvent) event;
            if (damageEvent.getType() == EventType.DAMAGE_PLAYER) {
                Player targetPlayer = game.getPlayer(event.getTargetId());
                if (targetPlayer != null) {
                    targetPlayer.damage(CardUtil.addWithOverflowCheck(damageEvent.getAmount(), controller.rollDice(game, 6)), damageEvent.getSourceId(), game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), event.getAppliedEffects());
                    return true;
                }
            } else {
                Permanent targetPermanent = game.getPermanent(event.getTargetId());
                if (targetPermanent != null) {
                    targetPermanent.damage(CardUtil.addWithOverflowCheck(damageEvent.getAmount(), controller.rollDice(game, 6)), damageEvent.getSourceId(), game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), event.getAppliedEffects());
                    return true;
                }
            }
        }
        return false;
    }
}
