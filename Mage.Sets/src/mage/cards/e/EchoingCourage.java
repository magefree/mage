package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class EchoingCourage extends CardImpl {

    public EchoingCourage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");


        // Target creature and all other creatures with the same name as that creature get +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new EchoingCourageEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private EchoingCourage(final EchoingCourage card) {
        super(card);
    }

    @Override
    public EchoingCourage copy() {
        return new EchoingCourage(this);
    }
}

class EchoingCourageEffect extends OneShotEffect {

    public EchoingCourageEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target creature and all other creatures with the same name as that creature get +2/+2 until end of turn";
    }

    private EchoingCourageEffect(final EchoingCourageEffect effect) {
        super(effect);
    }

    @Override
    public EchoingCourageEffect copy() {
        return new EchoingCourageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (targetPermanent != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            if (CardUtil.haveEmptyName(targetPermanent)) {
                filter.add(new PermanentIdPredicate(targetPermanent.getId()));  // if no name (face down creature) only the creature itself is selected
            } else {
                filter.add(new NamePredicate(targetPermanent.getName()));
            }
            ContinuousEffect effect = new BoostAllEffect(2, 2, Duration.EndOfTurn, filter, false);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
