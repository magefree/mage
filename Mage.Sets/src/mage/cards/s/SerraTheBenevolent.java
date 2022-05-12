package mage.cards.s;

import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.command.emblems.SerraTheBenevolentEmblem;
import mage.game.permanent.token.AngelVigilanceToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SerraTheBenevolent extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("Creatures you control with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public SerraTheBenevolent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SERRA);
        this.setStartingLoyalty(4);

        // +2: Creatures you control with flying get +1/+1 until end of turn.
        this.addAbility(new LoyaltyAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn, filter)
                .setText("Creatures you control with flying get +1/+1 until end of turn"), 2));

        // -3: Create a 4/4 white Angel creature token with flying and vigilance.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new AngelVigilanceToken()), -3));

        // -6: You get an emblem with "If you control a creature, damage that would reduce your life total to less than 1 reduces it to 1 instead."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new SerraTheBenevolentEmblem()), -6));
    }

    private SerraTheBenevolent(final SerraTheBenevolent card) {
        super(card);
    }

    @Override
    public SerraTheBenevolent copy() {
        return new SerraTheBenevolent(this);
    }
}
