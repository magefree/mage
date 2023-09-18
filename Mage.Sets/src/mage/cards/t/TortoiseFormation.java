package mage.cards.t;

import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class TortoiseFormation extends CardImpl {

    public TortoiseFormation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Creatures you control gain shroud until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                ShroudAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ));
    }

    private TortoiseFormation(final TortoiseFormation card) {
        super(card);
    }

    @Override
    public TortoiseFormation copy() {
        return new TortoiseFormation(this);
    }
}
