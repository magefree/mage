
package mage.cards.c;

import java.util.UUID;
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
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.EmptyCopyApplier;

/**
 *
 * @author jeffwadsworth
 */
public final class Cytoshape extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonlegendary creature");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public Cytoshape(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}{U}");

        // Choose a nonlegendary creature on the battlefield. Target creature becomes a copy of that creature until end of turn.
        this.getSpellAbility().addEffect(new CytoshapeEffect());
        Target target = new TargetCreaturePermanent(1, 1, filter, true);
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);

        FilterCreaturePermanent filter2 = new FilterCreaturePermanent("target creature that will become a copy of that chosen creature");
        target = new TargetCreaturePermanent(filter2);
        target.setTargetTag(2);
        this.getSpellAbility().addTarget(target);

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

    public CytoshapeEffect() {
        super(Outcome.Copy);
        this.staticText = "Choose a nonlegendary creature on the battlefield. Target creature becomes a copy of that creature until end of turn.";
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
        Permanent copyFrom = game.getPermanent(getTargetPointer().getFirst(game, ability));
        if (copyFrom != null) {
            Permanent copyTo = game.getPermanentOrLKIBattlefield(ability.getTargets().get(1).getFirstTarget());
            if (copyTo != null) {
                game.copyPermanent(Duration.EndOfTurn, copyFrom, copyTo.getId(), ability, new EmptyCopyApplier());
            }
        }
        return true;
    }
}
