package mage.cards.i;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.HighestCMCOfPermanentValue;
import mage.abilities.dynamicvalue.common.SacrificeCostConvertedMana;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IlluminorSzeras extends CardImpl {

    private static final DynamicValue xValue = new SacrificeCostConvertedMana("creature");
    private static final DynamicValue netValue = new HighestCMCOfPermanentValue(
            StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, true
    );

    public IlluminorSzeras(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Secrets of the Soul -- {T}, Sacrifice another creature: Add an amount of {B} equal to the sacrificed creature's mana value.
        Ability ability = new DynamicManaAbility(
                Mana.BlackMana(1), xValue, new TapSourceCost(), "add an amount of {B} " +
                "equal to the sacrificed creature's mana value", false, netValue
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability.withFlavorWord("Secrets of the Soul"));
    }

    private IlluminorSzeras(final IlluminorSzeras card) {
        super(card);
    }

    @Override
    public IlluminorSzeras copy() {
        return new IlluminorSzeras(this);
    }
}
