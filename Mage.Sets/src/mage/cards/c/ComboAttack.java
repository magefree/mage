package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterTeamCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ComboAttack extends CardImpl {

    private static final FilterPermanent filter = new FilterTeamCreaturePermanent("creatures your team controls");

    public ComboAttack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Two target creatures your team controls each deal damage equal to their power to target creature.
        this.getSpellAbility().addEffect(new ComboAttackEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(2, filter));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
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
        if (source.getTargets().size() < 2) {
            return false;
        }
        Permanent permanent3 = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent3 == null) {
            return false;
        }
        // You can’t cast Combo Attack without targeting two creatures your team controls.
        // If one of those creatures is an illegal target as Combo Attack resolves,
        // the other will still deal damage equal to its power. (2018-06-08)
        for (UUID id : source.getTargets().get(0).getTargets()) {
            Permanent permanent = game.getPermanent(id);
            if (permanent != null) {
                permanent3.damage(permanent.getPower().getValue(), permanent.getId(), source, game);
            }
        }
        return true;
    }
}
