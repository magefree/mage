package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.command.emblems.MuYanlingSkyDancerEmblem;
import mage.game.permanent.token.MuYanlingSkyDancerToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MuYanlingSkyDancer extends CardImpl {

    public MuYanlingSkyDancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.YANLING);
        this.setStartingLoyalty(2);

        // +2: Until your next turn, up to one target creature gets -2/-0 and loses flying.
        Ability ability = new LoyaltyAbility(new BoostTargetEffect(
                -2, 0, Duration.UntilYourNextTurn
        ).setText("Until your next turn, up to one target creature gets -2/-0"), 2);
        ability.addEffect(new LoseAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.UntilYourNextTurn
        ).setText("and loses flying"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // −3: Create a 4/4 blue Elemental Bird creature token with flying.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new MuYanlingSkyDancerToken()), -3));

        // −8: You get an emblem with "Islands you control have '{T}: Draw a card'."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new MuYanlingSkyDancerEmblem()), -8));
    }

    private MuYanlingSkyDancer(final MuYanlingSkyDancer card) {
        super(card);
    }

    @Override
    public MuYanlingSkyDancer copy() {
        return new MuYanlingSkyDancer(this);
    }
}
