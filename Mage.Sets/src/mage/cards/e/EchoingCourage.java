package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    EchoingCourageEffect() {
        super(Outcome.Benefit);
        this.staticText = "target creature and all other creatures with the " +
                "same name as that creature get +2/+2 until end of turn";
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
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetPermanent == null) {
            return false;
        }
        Set<Card> set = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT_CREATURE,
                        source.getControllerId(), source, game
                )
                .stream()
                .filter(permanent -> permanent.sharesName(targetPermanent, game))
                .collect(Collectors.toSet());
        set.add(targetPermanent);
        game.addEffect(new BoostTargetEffect(2, 2)
                .setTargetPointer(new FixedTargets(set, game)), source);
        return true;
    }
}
