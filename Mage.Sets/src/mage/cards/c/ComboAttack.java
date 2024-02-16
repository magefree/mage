package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterTeamCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class ComboAttack extends CardImpl {

    public ComboAttack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Two target creatures your team controls each deal damage equal to their power to target creature.
        this.getSpellAbility().addEffect(new ComboAttackEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2, 2, new FilterTeamCreaturePermanent(), false));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(1));
    }

    private ComboAttack(final ComboAttack card) {
        super(card);
    }

    @Override
    public ComboAttack copy() {
        return new ComboAttack(this);
    }
}

class ComboAttackEffect extends OneShotEffect {

    ComboAttackEffect() {
        super(Outcome.Benefit);
        this.staticText = "Two target creatures your team controls each deal damage equal to their power to target creature";
    }

    private ComboAttackEffect(final ComboAttackEffect effect) {
        super(effect);
    }

    @Override
    public ComboAttackEffect copy() {
        return new ComboAttackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().size() < 2 || source.getTargets().get(0).getTargets().size() < 2) {
            return false;
        }
        Permanent permanent1 = game.getPermanent(source.getTargets().get(0).getTargets().get(0));
        Permanent permanent2 = game.getPermanent(source.getTargets().get(0).getTargets().get(1));
        Permanent permanent3 = game.getPermanent(source.getTargets().get(1).getTargets().get(0));
        if (permanent3 == null) {
            return false;
        }
        if (permanent1 != null) {
            permanent3.damage(permanent1.getPower().getValue(), permanent1.getId(), source, game, false, true);
        }
        if (permanent2 != null) {
            permanent3.damage(permanent2.getPower().getValue(), permanent2.getId(), source, game, false, true);
        }
        return true;
    }
}
