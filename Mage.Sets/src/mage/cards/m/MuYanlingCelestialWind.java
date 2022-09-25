package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MuYanlingCelestialWind extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public MuYanlingCelestialWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.YANLING);
        this.setStartingLoyalty(5);

        // +1: Until your next turn, up to one target creature gets -5/-0.
        Ability ability = new LoyaltyAbility(new BoostTargetEffect(
                -5, 0, Duration.UntilYourNextTurn
        ).setText("Until your next turn, up to one target creature gets -5/-0."), 1);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // −3: Return up to two target creatures to their owners' hands.
        ability = new LoyaltyAbility(new ReturnToHandTargetEffect(), -3);
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);

        // −7: Creatures you control with flying get +5/+5 until end of turn.
        this.addAbility(new LoyaltyAbility(new BoostAllEffect(
                5, 5, Duration.EndOfTurn, filter, false
        ), -7));
    }

    private MuYanlingCelestialWind(final MuYanlingCelestialWind card) {
        super(card);
    }

    @Override
    public MuYanlingCelestialWind copy() {
        return new MuYanlingCelestialWind(this);
    }
}
