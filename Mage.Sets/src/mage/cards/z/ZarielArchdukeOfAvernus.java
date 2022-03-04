package mage.cards.z;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.command.emblems.ZarielArchdukeOfAvernusEmblem;
import mage.game.permanent.token.DevilToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZarielArchdukeOfAvernus extends CardImpl {

    public ZarielArchdukeOfAvernus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZARIEL);
        this.setStartingLoyalty(4);

        // +1: Creatures you control get +1/+0 and gain haste until end of turn.
        Ability ability = new LoyaltyAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn
        ).setText("creatures you control get +1/+0"), 1);
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and gain haste until end of turn"));
        this.addAbility(ability);

        // 0: Create a 1/1 red Devil creature token with "When this creature dies, it deals 1 damage to any target."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new DevilToken()), 0));

        // −6: You get an emblem with "At the end of the first combat phase on your turn, untap target creature you control. After this phase, there is an additional combat phase."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new ZarielArchdukeOfAvernusEmblem()), -6));
    }

    private ZarielArchdukeOfAvernus(final ZarielArchdukeOfAvernus card) {
        super(card);
    }

    @Override
    public ZarielArchdukeOfAvernus copy() {
        return new ZarielArchdukeOfAvernus(this);
    }
}
