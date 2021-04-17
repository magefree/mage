package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RookieMistake extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new AnotherTargetPredicate(2));
    }

    public RookieMistake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Until end of turn, target creature gets +0/+2 and another target creature gets -2/-0.
        this.getSpellAbility().addEffect(new RookieMistakeEffect());
        TargetPermanent target = new TargetCreaturePermanent();
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target.withChooseHint("+0/+2"));
        target = new TargetPermanent(filter);
        target.setTargetTag(2);
        this.getSpellAbility().addTarget(target.withChooseHint("-2/-0"));
    }

    private RookieMistake(final RookieMistake card) {
        super(card);
    }

    @Override
    public RookieMistake copy() {
        return new RookieMistake(this);
    }
}

class RookieMistakeEffect extends OneShotEffect {

    RookieMistakeEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "until end of turn, target creature gets +0/+2 and another target creature gets -2/-0";
    }

    private RookieMistakeEffect(final RookieMistakeEffect effect) {
        super(effect);
    }

    @Override
    public RookieMistakeEffect copy() {
        return new RookieMistakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            ContinuousEffect effect = new BoostTargetEffect(0, 2, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            ContinuousEffect effect = new BoostTargetEffect(-2, 0, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}
