package mage.cards.t;

import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class TowerDefense extends CardImpl {

    public TowerDefense(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Creatures you control get +0/+5 and gain reach until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(
                0, 5, Duration.EndOfTurn
        ).setText("creatures you control get +0/+5"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                ReachAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and gain reach until end of turn"));
    }

    private TowerDefense(final TowerDefense card) {
        super(card);
    }

    @Override
    public TowerDefense copy() {
        return new TowerDefense(this);
    }
}
