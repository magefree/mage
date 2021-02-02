
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author LevelX2
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

class TurnTheTablesEffect extends PreventDamageToControllerEffect {

    public TurnTheTablesEffect() {
        super(Duration.EndOfTurn, true, false, Integer.MAX_VALUE);
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
    protected PreventionEffectData preventDamageAction(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionEffectData = super.preventDamageAction(event, source, game);
        int damage = preventionEffectData.getPreventedDamage();
        if (damage > 0) {
            Permanent attackingCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (attackingCreature != null) {
                attackingCreature.damage(damage, source.getSourceId(), source, game, false, true);
            }
        }
        return preventionEffectData;
    }

}
