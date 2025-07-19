package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.YouCastExactOneSpellThisTurnCondition;
import mage.abilities.decorator.ConditionalCostModificationEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UthrosPsionicist extends CardImpl {

    public UthrosPsionicist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.JELLYFISH);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // The second spell you cast each turn costs {2} less to cast.
        this.addAbility(new SimpleStaticAbility(new ConditionalCostModificationEffect(
                new SpellsCostReductionControllerEffect(StaticFilters.FILTER_CARD, 2),
                YouCastExactOneSpellThisTurnCondition.instance, "the second spell you cast each turn costs {2} less to cast"
        )));
    }

    private UthrosPsionicist(final UthrosPsionicist card) {
        super(card);
    }

    @Override
    public UthrosPsionicist copy() {
        return new UthrosPsionicist(this);
    }
}
