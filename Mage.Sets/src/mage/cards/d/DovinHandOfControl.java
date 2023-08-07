package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DovinHandOfControl extends CardImpl {

    private static final FilterCard filter = new FilterCard("Artifact, instant, and sorcery spells");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public DovinHandOfControl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W/U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOVIN);
        this.setStartingLoyalty(5);

        // Artifact, instant, and sorcery spells your opponents cast cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostIncreasingAllEffect(1, filter, TargetController.OPPONENT)));

        // -1: Until your next turn, prevent all damage that would be dealt to and dealt by target permanent an opponent controls.
        Ability ability = new LoyaltyAbility(new PreventDamageToTargetEffect(
                Duration.UntilYourNextTurn
        ).setText("Until your next turn, prevent all damage that would be dealt to"), -1);
        ability.addEffect(new PreventDamageByTargetEffect(
                Duration.UntilYourNextTurn
        ).setText("and dealt by target permanent an opponent controls"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT));
        this.addAbility(ability);
    }

    private DovinHandOfControl(final DovinHandOfControl card) {
        super(card);
    }

    @Override
    public DovinHandOfControl copy() {
        return new DovinHandOfControl(this);
    }
}