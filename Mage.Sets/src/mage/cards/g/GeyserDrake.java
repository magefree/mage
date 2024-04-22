package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.decorator.ConditionalCostModificationEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GeyserDrake extends CardImpl {

    public GeyserDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As long as it's not your turn, spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new ConditionalCostModificationEffect(
                new SpellsCostReductionControllerEffect(StaticFilters.FILTER_CARD, 1),
                NotMyTurnCondition.instance, "as long as it's not your turn, " +
                "spells you cast cost {1} less to cast"
        )));
    }

    private GeyserDrake(final GeyserDrake card) {
        super(card);
    }

    @Override
    public GeyserDrake copy() {
        return new GeyserDrake(this);
    }
}
