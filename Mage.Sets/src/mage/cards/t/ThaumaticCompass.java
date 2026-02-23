package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.RemoveFromCombatTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThaumaticCompass extends TransformingDoubleFacedCard {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterLandPermanent("you control seven or more lands"),
            ComparisonType.MORE_THAN, 6, true
    );

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent("attacking creature an opponent controls");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public ThaumaticCompass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{2}",
                "Spires of Orazca",
                new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // Thaumatic Compass
        // {3}, {T}: Search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true),
                new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.getLeftHalfCard().addAbility(ability);

        // At the beginning of your end step, if you control seven or more lands, transform Thaumatic Compass.
        this.getLeftHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(new TransformSourceEffect())
                .withInterveningIf(condition).addHint(LandsYouControlHint.instance));

        // Spires of Orazca
        // {T}: Add {C}.
        this.getRightHalfCard().addAbility(new ColorlessManaAbility());

        // {T}: Untap target attacking creature an opponent controls and remove it from combat.
        Ability ability2 = new SimpleActivatedAbility(
                new UntapTargetEffect()
                        .setText("Untap target attacking creature an opponent controls"),
                new TapSourceCost()
        );
        ability2.addEffect(new RemoveFromCombatTargetEffect().setText("and remove it from combat"));
        ability2.addTarget(new TargetPermanent(filter));
        this.getRightHalfCard().addAbility(ability2);
    }

    private ThaumaticCompass(final ThaumaticCompass card) {
        super(card);
    }

    @Override
    public ThaumaticCompass copy() {
        return new ThaumaticCompass(this);
    }
}
