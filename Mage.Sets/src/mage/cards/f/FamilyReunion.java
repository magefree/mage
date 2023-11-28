package mage.cards.f;

import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FamilyReunion extends CardImpl {

    public FamilyReunion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Choose one --
        // * Creatures you control get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn));

        // * Creatures you control gain hexproof until end of turn.
        this.getSpellAbility().addMode(new Mode(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        )));
    }

    private FamilyReunion(final FamilyReunion card) {
        super(card);
    }

    @Override
    public FamilyReunion copy() {
        return new FamilyReunion(this);
    }
}
