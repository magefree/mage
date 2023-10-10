
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.AwakenAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class EncirclingFissure extends CardImpl {

    public EncirclingFissure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Prevent all combat damage that would be dealt this turn by creatures target opponent controls.
        this.getSpellAbility().addEffect(new EncirclingFissurePreventEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Awaken 2 —{4}{W}</i>
        this.addAbility(new AwakenAbility(this, 2, "{4}{W}"));
    }

    private EncirclingFissure(final EncirclingFissure card) {
        super(card);
    }

    @Override
    public EncirclingFissure copy() {
        return new EncirclingFissure(this);
    }
}

class EncirclingFissurePreventEffect extends PreventionEffectImpl {

    public EncirclingFissurePreventEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, true, false);
        staticText = "Prevent all combat damage that would be dealt this turn by creatures target opponent controls";
    }

    private EncirclingFissurePreventEffect(final EncirclingFissurePreventEffect effect) {
        super(effect);
    }

    @Override
    public EncirclingFissurePreventEffect copy() {
        return new EncirclingFissurePreventEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamageEvent && event.getAmount() > 0) {
            DamageEvent damageEvent = (DamageEvent) event;
            if (damageEvent.isCombatDamage()) {
                Permanent permanent = game.getPermanent(damageEvent.getSourceId());
                if (permanent != null
                        && permanent.isCreature(game)
                        && permanent.isControlledBy(getTargetPointer().getFirst(game, source))) {
                    return true;
                }
            }
        }
        return false;
    }
}
