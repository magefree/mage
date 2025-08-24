package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CutPropulsion extends CardImpl {

    public CutPropulsion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Target creature deals damage to itself equal to its power. If that creature has flying, it deals twice that much damage to itself instead.
        this.getSpellAbility().addEffect(new CutPropulsionEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private CutPropulsion(final CutPropulsion card) {
        super(card);
    }

    @Override
    public CutPropulsion copy() {
        return new CutPropulsion(this);
    }
}

class CutPropulsionEffect extends OneShotEffect {

    CutPropulsionEffect() {
        super(Outcome.Benefit);
        staticText = "target creature deals damage to itself equal to its power. " +
                "If that creature has flying, it deals twice that much damage to itself instead";
    }

    private CutPropulsionEffect(final CutPropulsionEffect effect) {
        super(effect);
    }

    @Override
    public CutPropulsionEffect copy() {
        return new CutPropulsionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int damage = (permanent.getAbilities(game).containsClass(FlyingAbility.class) ? 2 : 1) * permanent.getPower().getValue();
        return damage > 0 && permanent.damage(damage, permanent.getId(), source, game) > 0;
    }
}
