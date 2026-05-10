package mage.cards.f;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author muz
 */
public final class FurygaleFlocking extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY);
    private static final Hint hint = new ValueHint("Instant and sorcery cards in your graveyard", xValue);

    public FurygaleFlocking(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{8}{R}{R}");

        // This spell costs {1} less to cast for each instant and sorcery card in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue)
        ).setRuleAtTheTop(true).addHint(hint));

        // For each opponent, create two 3/3 blue and red Elemental creature tokens with flying that attack that opponent this turn if able. They gain haste until end of turn.
    }

    private FurygaleFlocking(final FurygaleFlocking card) {
        super(card);
    }

    @Override
    public FurygaleFlocking copy() {
        return new FurygaleFlocking(this);
    }
}
