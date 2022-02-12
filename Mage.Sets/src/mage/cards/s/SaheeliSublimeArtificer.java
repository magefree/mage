package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ServoToken;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.EmptyCopyApplier;

/**
 * @author TheElk801
 */
public final class SaheeliSublimeArtificer extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledArtifactPermanent();
    private static final FilterPermanent filter2
            = new FilterControlledPermanent("artifact or creature you control");

    static {
        filter.add(new AnotherTargetPredicate(1));
        filter2.add(new AnotherTargetPredicate(2));
        filter2.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public SaheeliSublimeArtificer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{U/R}{U/R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SAHEELI);
        this.setStartingLoyalty(5);

        // Whenever you cast a noncreature spell, create a 1/1 colorless Servo artifact creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new ServoToken()), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // -2: Target artifact you control becomes a copy of another target artifact or creature you control until end of turn, except it's an artifact in addition to its other types.
        Ability ability = new LoyaltyAbility(new SaheeliSublimeArtificerEffect(), -2);
        Target target = new TargetPermanent(filter);
        target.setTargetTag(1);
        ability.addTarget(target);
        target = new TargetPermanent(filter2);
        target.setTargetTag(2);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private SaheeliSublimeArtificer(final SaheeliSublimeArtificer card) {
        super(card);
    }

    @Override
    public SaheeliSublimeArtificer copy() {
        return new SaheeliSublimeArtificer(this);
    }
}

class SaheeliSublimeArtificerEffect extends OneShotEffect {

    SaheeliSublimeArtificerEffect() {
        super(Outcome.Benefit);
        staticText = "Target artifact you control becomes a copy of another target artifact or creature you control"
                + " until end of turn, except it's an artifact in addition to its other types.";
    }

    private SaheeliSublimeArtificerEffect(final SaheeliSublimeArtificerEffect effect) {
        super(effect);
    }

    @Override
    public SaheeliSublimeArtificerEffect copy() {
        return new SaheeliSublimeArtificerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent copyTo = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (copyTo != null) {
            Permanent copyFrom = game.getPermanentOrLKIBattlefield(source.getTargets().get(1).getFirstTarget());
            if (copyFrom != null) {
                game.copyPermanent(Duration.EndOfTurn, copyFrom, copyTo.getId(), source, new EmptyCopyApplier());
                ContinuousEffect effect = new AddCardTypeTargetEffect(Duration.EndOfTurn, CardType.ARTIFACT);
                effect.setTargetPointer(new FixedTarget(copyTo, game));
                game.addEffect(effect, source);
            }
        }
        return true;
    }
}
