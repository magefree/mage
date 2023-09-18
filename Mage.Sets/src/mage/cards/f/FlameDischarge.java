package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.common.ControlledModifiedCreatureAsSpellCastCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.watchers.common.ControlledModifiedCreatureAsSpellCastWatcher;

/**
 *
 * @author weirddan455
 */
public final class FlameDischarge extends CardImpl {

    public FlameDischarge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}");

        // Flame Discharge deals X damage to target creature or planeswalker. If you controlled a modified creature as you cast this spell, it deals X plus 2 damage instead.
        this.getSpellAbility().addEffect(new FlameDischargeEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addWatcher(new ControlledModifiedCreatureAsSpellCastWatcher());
    }

    private FlameDischarge(final FlameDischarge card) {
        super(card);
    }

    @Override
    public FlameDischarge copy() {
        return new FlameDischarge(this);
    }
}

class FlameDischargeEffect extends OneShotEffect {

    public FlameDischargeEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals X damage to target creature or planeswalker. If you controlled a modified creature as you cast this spell, it deals X plus 2 damage instead";
    }

    private FlameDischargeEffect(final FlameDischargeEffect effect) {
        super(effect);
    }

    @Override
    public FlameDischargeEffect copy() {
        return new FlameDischargeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int damageAmount = source.getManaCostsToPay().getX();
        if (ControlledModifiedCreatureAsSpellCastCondition.instance.apply(game, source)) {
            damageAmount += 2;
        }
        permanent.damage(damageAmount, source, game);
        return true;
    }
}
