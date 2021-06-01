package mage.cards.s;

import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SanctumWeaver extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledEnchantmentPermanent();
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Enchantments you control", xValue);

    public SanctumWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {T}: Add X mana of any one color, where X is the number of enchantments you control.
        this.addAbility(new DynamicManaAbility(
                new Mana(0, 0, 0, 0, 0, 0, 1, 0),
                xValue, new TapSourceCost(), "Add X mana of any one color, " +
                "where X is the number of enchantments you control", true
        ).addHint(hint));
    }

    private SanctumWeaver(final SanctumWeaver card) {
        super(card);
    }

    @Override
    public SanctumWeaver copy() {
        return new SanctumWeaver(this);
    }
}
