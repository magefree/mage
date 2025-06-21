package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.functions.EmptyCopyApplier;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Cytoshape extends CardImpl {

    public Cytoshape(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}{U}");

        // Choose a nonlegendary creature on the battlefield. Target creature becomes a copy of that creature until end of turn.
        this.getSpellAbility().addEffect(new CytoshapeEffect());
        this.getSpellAbility().addTarget(new TargetPermanent().withChooseHint("to become a copy"));
    }

    private Cytoshape(final Cytoshape card) {
        super(card);
    }

    @Override
    public Cytoshape copy() {
        return new Cytoshape(this);
    }
}

class CytoshapeEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonlegendary creature");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    CytoshapeEffect() {
        super(Outcome.Copy);
        this.staticText = "Choose a nonlegendary creature on the battlefield. " +
                "Target creature becomes a copy of that creature until end of turn.";
    }

    private CytoshapeEffect(final CytoshapeEffect effect) {
        super(effect);
    }

    @Override
    public CytoshapeEffect copy() {
        return new CytoshapeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability ability) {
        Target target = new TargetPermanent(1, 1, filter, true);
        target.choose(Outcome.Copy, ability.getControllerId(), ability, game);
        Permanent copyFrom = game.getPermanent(target.getFirstTarget());
        if (copyFrom != null) {
            Permanent copyTo = game.getPermanentOrLKIBattlefield(ability.getTargets().get(0).getFirstTarget());
            if (copyTo != null) {
                game.copyPermanent(Duration.EndOfTurn, copyFrom, copyTo.getId(), ability, new EmptyCopyApplier());
            }
        }
        return true;
    }
}
