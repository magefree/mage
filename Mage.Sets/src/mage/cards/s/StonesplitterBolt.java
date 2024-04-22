package mage.cards.s;

import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.BargainAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class StonesplitterBolt extends CardImpl {

    public StonesplitterBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}");

        // Bargain
        this.addAbility(new BargainAbility());

        // Stonesplitter Bolt deals X damage to target creature or planeswalker. If this spell was bargained, it deals twice X damage to that permanent instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(new MultipliedValue(ManacostVariableValue.REGULAR, 2)),
                new DamageTargetEffect(ManacostVariableValue.REGULAR),
                BargainedCondition.instance,
                "{this} deals X damage to target creature or planeswalker. If this spell "
                        + "was bargained, it deals twice X damage to that permanent instead."
        ));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private StonesplitterBolt(final StonesplitterBolt card) {
        super(card);
    }

    @Override
    public StonesplitterBolt copy() {
        return new StonesplitterBolt(this);
    }
}
