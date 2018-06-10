
package mage.cards.b;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.continuous.SetPowerToughnessAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BiomassMutation extends CardImpl {

    public BiomassMutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{G/U}{G/U}");


        // Creatures you control have base power and toughness X/X until end of turn.
        DynamicValue variableMana = new ManacostVariableValue();
        this.getSpellAbility().addEffect(new SetPowerToughnessAllEffect(variableMana, variableMana, Duration.EndOfTurn, new FilterControlledCreaturePermanent("Creatures you control"), true));
    }

    public BiomassMutation(final BiomassMutation card) {
        super(card);
    }

    @Override
    public BiomassMutation copy() {
        return new BiomassMutation(this);
    }
}
