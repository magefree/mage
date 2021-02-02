package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SoldierToken;

/**
 *
 * @author TheElk801
 */
public final class HeroicReinforcements extends CardImpl {

    public HeroicReinforcements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{W}");

        // Create two 1/1 white Soldier creature tokens. Until end of turn, creatures you control get +1/+1 and gain haste.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new SoldierToken(), 2
        ));
        this.getSpellAbility().addEffect(new BoostControlledEffect(
                1, 1, Duration.EndOfTurn
        ).setText("Until end of turn, creatures you control get +1/+1"));
        this.getSpellAbility().addEffect(
                new GainAbilityControlledEffect(
                        HasteAbility.getInstance(),
                        Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ).setText("and gain haste")
        );
    }

    private HeroicReinforcements(final HeroicReinforcements card) {
        super(card);
    }

    @Override
    public HeroicReinforcements copy() {
        return new HeroicReinforcements(this);
    }
}
