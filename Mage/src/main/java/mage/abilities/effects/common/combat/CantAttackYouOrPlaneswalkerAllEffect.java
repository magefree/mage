package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public class CantAttackYouOrPlaneswalkerAllEffect extends RestrictionEffect {

    private final FilterCreaturePermanent filterAttacker;

    public CantAttackYouOrPlaneswalkerAllEffect(Duration duration) {
        this(duration, new FilterCreaturePermanent());
    }

    public CantAttackYouOrPlaneswalkerAllEffect(Duration duration, FilterCreaturePermanent filter) {
        super(duration, Outcome.Benefit);
        this.filterAttacker = filter;
        staticText = "Creatures can't attack you";
    }

    CantAttackYouOrPlaneswalkerAllEffect(final CantAttackYouOrPlaneswalkerAllEffect effect) {
        super(effect);
        this.filterAttacker = effect.filterAttacker;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filterAttacker.match(permanent, source.getControllerId(), source, game);
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }

        if (defenderId.equals(source.getControllerId())) {
            return false;
        }
        Permanent planeswalker = game.getPermanent(defenderId);
        return planeswalker == null || !planeswalker.isControlledBy(source.getControllerId());
    }

    @Override
    public CantAttackYouOrPlaneswalkerAllEffect copy() {
        return new CantAttackYouOrPlaneswalkerAllEffect(this);
    }
}
