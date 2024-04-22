package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.CaseAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.condition.common.SolvedSourceCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.common.CaseSolvedHint;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.SuspectedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SkeletonToken2;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author DominionSpy
 */
public final class CaseOfTheStashedSkeleton extends CardImpl {

    static final FilterPermanent filter = new FilterControlledPermanent(SubType.SKELETON, "You control no suspected Skeletons");

    static {
        filter.add(SuspectedPredicate.instance);
    }

    public CaseOfTheStashedSkeleton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.CASE);

        // When this Case enters the battlefield, create a 2/1 black Skeleton creature token and suspect it.
        Ability initialAbility = new EntersBattlefieldTriggeredAbility(
                new CaseOfTheStashedSkeletonEffect());
        // To solve -- You control no suspected Skeletons.
        Condition toSolveCondition = new PermanentsOnTheBattlefieldCondition(
                filter, ComparisonType.EQUAL_TO, 0, true);
        // Solved -- {1}{B}, Sacrifice this Case: Search your library for a card, put it into your hand, then shuffle. Activate only as a sorcery.
        Ability solvedAbility = new ConditionalActivatedAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false)
                        .setText("Search your library for a card, put it into your hand, then shuffle. Activate only as a sorcery."),
                new ManaCostsImpl<>("{1}{B}"),
                SolvedSourceCondition.SOLVED)
                .setTiming(TimingRule.SORCERY);
        solvedAbility.addCost(new SacrificeSourceCost().setText("sacrifice this Case"));

        this.addAbility(new CaseAbility(initialAbility, toSolveCondition, solvedAbility)
                .addHint(new CaseOfTheStashedSkeletonHint(toSolveCondition)));
    }

    private CaseOfTheStashedSkeleton(final CaseOfTheStashedSkeleton card) {
        super(card);
    }

    @Override
    public CaseOfTheStashedSkeleton copy() {
        return new CaseOfTheStashedSkeleton(this);
    }
}

class CaseOfTheStashedSkeletonEffect extends CreateTokenEffect {

    CaseOfTheStashedSkeletonEffect() {
        super(new SkeletonToken2());
        staticText = "create a 2/1 black Skeleton creature token and suspect it";
    }

    private CaseOfTheStashedSkeletonEffect(final CaseOfTheStashedSkeletonEffect effect) {
        super(effect);
    }

    @Override
    public CaseOfTheStashedSkeletonEffect copy() {
        return new CaseOfTheStashedSkeletonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        super.apply(game, source);
        Permanent token = game.getPermanent(this.getLastAddedTokenIds().stream().findFirst().orElse(null));
        if (token != null) {
            token.setSuspected(true, game, source);
            return true;
        }
        return false;
    }
}

class CaseOfTheStashedSkeletonHint extends CaseSolvedHint {

    CaseOfTheStashedSkeletonHint(Condition condition) {
        super(condition);
    }

    private CaseOfTheStashedSkeletonHint(final CaseOfTheStashedSkeletonHint hint) {
        super(hint);
    }

    @Override
    public CaseOfTheStashedSkeletonHint copy() {
        return new CaseOfTheStashedSkeletonHint(this);
    }

    @Override
    public String getConditionText(Game game, Ability ability) {
        int suspectedSkeletons = game.getBattlefield()
                .count(CaseOfTheStashedSkeleton.filter, ability.getControllerId(),
                        ability, game);
        return "Suspected skeletons: " + suspectedSkeletons + " (need 0).";
    }
}
