package mage.cards.g;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BeastToken;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class GarrukWildspeaker extends CardImpl {

    public GarrukWildspeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{2}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GARRUK);

        this.setStartingLoyalty(3);

        // +1: Untap two target lands.
        LoyaltyAbility ability1 = new LoyaltyAbility(new UntapTargetEffect(), 1);
        ability1.addTarget(new TargetLandPermanent(2));
        this.addAbility(ability1);

        // −1: Create a 3/3 green Beast creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new BeastToken()), -1));

        // −4: Creatures you control get +3/+3 and gain trample until end of turn.
        Effect boostEffect = new BoostControlledEffect(3, 3, Duration.EndOfTurn)
                .setText("Creatures you control get +3/+3");
        Effect trampleEffect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES)
                .setText("and gain trample until end of turn");
        LoyaltyAbility ability2 = new LoyaltyAbility(boostEffect, -4);
        ability2.addEffect(trampleEffect);
        this.addAbility(ability2);
    }

    private GarrukWildspeaker(final GarrukWildspeaker card) {
        super(card);
    }

    @Override
    public GarrukWildspeaker copy() {
        return new GarrukWildspeaker(this);
    }
}
