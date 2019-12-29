package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BandTogether extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creatures you control");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(new AnotherTargetPredicate(1));
        filter2.add(new AnotherTargetPredicate(2));
    }

    public BandTogether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Up to two target creatures you control each deal damage equal to their power to another target creature.
        this.getSpellAbility().addEffect(new BandTogetherEffect());
        Target target = new TargetPermanent(0, 2, filter, false);
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);
        target = new TargetPermanent(1, 1, filter2, false);
        target.setTargetTag(2);
        this.getSpellAbility().addTarget(target);
    }

    private BandTogether(final BandTogether card) {
        super(card);
    }

    @Override
    public BandTogether copy() {
        return new BandTogether(this);
    }
}

class BandTogetherEffect extends OneShotEffect {

    BandTogetherEffect() {
        super(Outcome.Benefit);
        this.staticText = "Up to two target creatures you control each deal damage equal to their power to another target creature.";
    }

    private BandTogetherEffect(final BandTogetherEffect effect) {
        super(effect);
    }

    @Override
    public BandTogetherEffect copy() {
        return new BandTogetherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().size() < 2) {
            return false;
        }

        Target damageTarget = source.getTargets().get(0);
        Target destTarget = source.getTargets().get(1);
        if (damageTarget.getTargets().isEmpty() || destTarget.getTargets().isEmpty()) {
            return false;
        }

        Permanent permanentDamage1 = damageTarget.getTargets().isEmpty() ? null : game.getPermanent(damageTarget.getTargets().get(0));
        Permanent permanentDamage2 = damageTarget.getTargets().size() < 2 ? null : game.getPermanent(damageTarget.getTargets().get(1));
        Permanent permanentDest = game.getPermanent(destTarget.getTargets().get(0));
        if (permanentDest == null) {
            return false;
        }

        if (permanentDamage1 != null) {
            permanentDest.damage(permanentDamage1.getPower().getValue(), permanentDamage1.getId(), game, false, true);
        }
        if (permanentDamage2 != null) {
            permanentDest.damage(permanentDamage2.getPower().getValue(), permanentDamage2.getId(), game, false, true);
        }
        return true;
    }
}
