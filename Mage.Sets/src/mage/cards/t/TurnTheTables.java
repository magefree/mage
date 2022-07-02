
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author LevelX2
 * @author sprangg
 */
public final class TurnTheTables extends CardImpl {

    public TurnTheTables(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}{W}");

        // All combat damage that would be dealt to you this turn is dealt to target attacking creature instead.
        this.getSpellAbility().addEffect(new TurnTheTablesEffect());
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    private TurnTheTables(final TurnTheTables card) {
        super(card);
    }

    @Override
    public TurnTheTables copy() {
        return new TurnTheTables(this);
    }
}

class TurnTheTablesEffect extends RedirectionEffect {

    public TurnTheTablesEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, UsageType.ACCORDING_DURATION);
        staticText = "All combat damage that would be dealt to you this turn is dealt to target attacking creature instead";
    }

    public TurnTheTablesEffect(final TurnTheTablesEffect effect) {
        super(effect);
    }

    @Override
    public TurnTheTablesEffect copy() {
        return new TurnTheTablesEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getPlayer(source.getControllerId()) == null || game.getPermanent(source.getFirstTarget()) == null) {
            return false;
        }
        DamageEvent damageEvent = (DamageEvent) event;
        if (!damageEvent.isCombatDamage() || !source.getControllerId().equals(damageEvent.getTargetId())) {
            return false;
        }
        this.redirectTarget = source.getTargets().get(0);
        return true;
    }

}