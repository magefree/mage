package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostManaValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class FaithHealer extends CardImpl {

    public FaithHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sacrifice an enchantment: You gain life equal to the sacrificed enchantment's mana value.
        this.addAbility(new SimpleActivatedAbility(new GainLifeEffect(SacrificeCostManaValue.ENCHANTMENT)
                .setText("you gain life equal to the sacrificed enchantment's mana value"),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ENCHANTMENT)));
    }

    private FaithHealer(final FaithHealer card) {
        super(card);
    }

    @Override
    public FaithHealer copy() {
        return new FaithHealer(this);
    }
}
