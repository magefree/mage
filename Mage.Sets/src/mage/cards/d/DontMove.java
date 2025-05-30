package mage.cards.d;

import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.common.delayed.UntilYourNextTurnDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 * @author jimga150, Susucr
 */
public final class DontMove extends CardImpl {

    // Based on Guan Yu's 1,000-Li March and Fire Giant's Fury
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creatures");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public DontMove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Destroy all tapped creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter, false));

        // Until your next turn, whenever a creature becomes tapped, destroy it.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new UntilYourNextTurnDelayedTriggeredAbility(
                        new BecomesTappedTriggeredAbility(
                                new DestroyTargetEffect().setText("destroy it"), false,
                                StaticFilters.FILTER_PERMANENT_CREATURE, true
                        )
                )
        ));

        // Ruling (2023-11-10)
        // > Don't Move won't affect a creature that enters the battlefield tapped.
    }

    private DontMove(final DontMove card) {
        super(card);
    }

    @Override
    public DontMove copy() {
        return new DontMove(this);
    }
}
