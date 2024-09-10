package mage.cards.k;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class KnowledgeIsPower extends CardImpl {

    public KnowledgeIsPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{U}");

        // Creatures you control get +X/+X, where X is the number of cards you've drawn this turn.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                CardsDrawnThisTurnDynamicValue.instance, CardsDrawnThisTurnDynamicValue.instance,
                Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURE, false
        ).setText("Creatures you control get +X/+X, where X is the number of cards you've drawn this turn")
        ).addHint(CardsDrawnThisTurnDynamicValue.getHint()));
    }

    private KnowledgeIsPower(final KnowledgeIsPower card) {
        super(card);
    }

    @Override
    public KnowledgeIsPower copy() {
        return new KnowledgeIsPower(this);
    }
}
