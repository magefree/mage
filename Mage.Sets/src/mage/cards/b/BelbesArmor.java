package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BelbesArmor extends CardImpl {

    public BelbesArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {X}, {tap}: Target creature gets -X/+X until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(
                new MultipliedValue(ManacostVariableValue.REGULAR, -1),
                ManacostVariableValue.REGULAR, Duration.EndOfTurn
        ).setText("Target creature gets -X/+X until end of turn"), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BelbesArmor(final BelbesArmor card) {
        super(card);
    }

    @Override
    public BelbesArmor copy() {
        return new BelbesArmor(this);
    }
}
