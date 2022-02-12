package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class DeadRingers extends CardImpl {

    public DeadRingers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Destroy two target nonblack creatures unless either one is a color the other isn't. They can't be regenerated.
        this.getSpellAbility().addEffect(new DeadRingersEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2, 2, StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK, false));
    }

    private DeadRingers(final DeadRingers card) {
        super(card);
    }

    @Override
    public DeadRingers copy() {
        return new DeadRingers(this);
    }
}

class DeadRingersEffect extends DestroyTargetEffect {

    public DeadRingersEffect() {
        super(true);
        staticText = "Destroy two target nonblack creatures unless either one is a color the other isn't. They can't be regenerated.";
    }

    public DeadRingersEffect(final DeadRingersEffect effect) {
        super(effect);
    }

    @Override
    public DeadRingersEffect copy() {
        return new DeadRingersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Target target = source.getTargets().get(0);
        if (target != null
                && target.getTargets().size() > 1) {
            Permanent first = game.getPermanentOrLKIBattlefield(target.getTargets().get(0));
            Permanent second = game.getPermanentOrLKIBattlefield(target.getTargets().get(1));
            if (first != null
                    && second != null
                    && first.getColor(game).equals(second.getColor(game))) {
                return super.apply(game, source);
            }
        }
        return false;
    }
}
