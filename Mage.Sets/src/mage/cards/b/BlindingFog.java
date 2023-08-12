package mage.cards.b;

import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BlindingFog extends CardImpl {

    public BlindingFog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Prevent all damage that would be dealt to creatures this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageToAllEffect(Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES));

        // Creatures you control gain hexproof until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, false));
    }

    private BlindingFog(final BlindingFog card) {
        super(card);
    }

    @Override
    public BlindingFog copy() {
        return new BlindingFog(this);
    }
}
