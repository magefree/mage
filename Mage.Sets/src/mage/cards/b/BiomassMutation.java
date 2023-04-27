
package mage.cards.b;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class BiomassMutation extends CardImpl {

    public BiomassMutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{G/U}{G/U}");


        // Creatures you control have base power and toughness X/X until end of turn.
        DynamicValue variableMana = ManacostVariableValue.REGULAR;
        this.getSpellAbility().addEffect(new SetBasePowerToughnessAllEffect(variableMana, variableMana, Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES, true));
    }

    private BiomassMutation(final BiomassMutation card) {
        super(card);
    }

    @Override
    public BiomassMutation copy() {
        return new BiomassMutation(this);
    }
}
