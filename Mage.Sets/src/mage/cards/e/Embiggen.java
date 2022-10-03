package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Embiggen extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("non-Brushwagg creature");

    static {
        filter.add(Predicates.not(SubType.BRUSHWAGG.getPredicate()));
    }

    public Embiggen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Until end of turn, target non-Brushwagg creature gets +1/+1 for each supertype, card type, and subtype it has.
        this.getSpellAbility().addEffect(new EmbiggenEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private Embiggen(final Embiggen card) {
        super(card);
    }

    @Override
    public Embiggen copy() {
        return new Embiggen(this);
    }
}

class EmbiggenEffect extends OneShotEffect {

    EmbiggenEffect() {
        super(Outcome.Benefit);
        staticText = "until end of turn, target non-Brushwagg creature " +
                "gets +1/+1 for each supertype, card type, and subtype it has";
    }

    private EmbiggenEffect(final EmbiggenEffect effect) {
        super(effect);
    }

    @Override
    public EmbiggenEffect copy() {
        return new EmbiggenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int count = permanent
                .getSuperType()
                .size()
                + permanent
                .getCardType(game)
                .size()
                + Arrays
                .stream(SubType.values())
                .filter(subType -> permanent.hasSubtype(subType, game))
                .mapToInt(x -> 1)
                .sum();
        if (count > 0) {
            game.addEffect(new BoostTargetEffect(count, count).setTargetPointer(new FixedTarget(permanent, game)), source);
            return true;
        }
        return false;
    }
}
// it's a perfectly cromulent card
