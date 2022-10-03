
package mage.cards.d;

import java.util.UUID;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;

/**
 *
 * @author LevelX2
 */
public final class DawnglowInfusion extends CardImpl {

    public DawnglowInfusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{G/W}");

        // You gain X life if {G} was spent to cast Dawnglow Infusion and X life if {W} was spent to cast it.
        DynamicValue xValue = ManacostVariableValue.REGULAR;
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(xValue),
                new ManaWasSpentCondition(ColoredManaSymbol.G), "You gain X life if {G} was spent to cast this spell"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(xValue),
                new ManaWasSpentCondition(ColoredManaSymbol.W), "and X life if {W} was spent to cast this spell"));
        this.getSpellAbility().addEffect(new InfoEffect("<i>(Do both if {G}{W} was spent.)</i>"));
    }

    private DawnglowInfusion(final DawnglowInfusion card) {
        super(card);
    }

    @Override
    public DawnglowInfusion copy() {
        return new DawnglowInfusion(this);
    }
}
