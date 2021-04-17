package mage.cards.e;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExhilaratingElocution extends CardImpl {

    public ExhilaratingElocution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{B}");

        // Put two +1/+1 counters on target creature you control. Other creatures you control get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new ExhilaratingElocutionEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private ExhilaratingElocution(final ExhilaratingElocution card) {
        super(card);
    }

    @Override
    public ExhilaratingElocution copy() {
        return new ExhilaratingElocution(this);
    }
}

class ExhilaratingElocutionEffect extends OneShotEffect {

    ExhilaratingElocutionEffect() {
        super(Outcome.Benefit);
        staticText = "put two +1/+1 counters on target creature you control. " +
                "Other creatures you control get +1/+1 until end of turn";
    }

    private ExhilaratingElocutionEffect(final ExhilaratingElocutionEffect effect) {
        super(effect);
    }

    @Override
    public ExhilaratingElocutionEffect copy() {
        return new ExhilaratingElocutionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.addCounters(CounterType.P1P1.createInstance(2), source.getControllerId(), source, game);
        FilterCreaturePermanent filterPermanent = new FilterCreaturePermanent();
        filterPermanent.add(Predicates.not(new MageObjectReferencePredicate(new MageObjectReference(permanent, game))));
        game.addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn, filterPermanent), source);
        return true;
    }
}
