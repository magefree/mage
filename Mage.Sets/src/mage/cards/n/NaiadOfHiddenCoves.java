package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.decorator.ConditionalCostModificationEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NaiadOfHiddenCoves extends CardImpl {

    public NaiadOfHiddenCoves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.NYMPH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // As long as it's not your turn, spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new ConditionalCostModificationEffect(
                new SpellsCostReductionControllerEffect(StaticFilters.FILTER_CARD, 1),
                NotMyTurnCondition.instance, "As long as it's not your turn, " +
                "spells you cast cost {1} less to cast."
        )).addHint(MyTurnHint.instance));
    }

    private NaiadOfHiddenCoves(final NaiadOfHiddenCoves card) {
        super(card);
    }

    @Override
    public NaiadOfHiddenCoves copy() {
        return new NaiadOfHiddenCoves(this);
    }
}
