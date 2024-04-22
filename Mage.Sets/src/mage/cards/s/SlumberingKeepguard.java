package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author Xanderhall
 */
public final class SlumberingKeepguard extends CardImpl {

    private static final DynamicValue value = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_ENCHANTMENT);

    public SlumberingKeepguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever an enchantment enters the battlefield under your control, scry 1.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new ScryEffect(1, false), new FilterEnchantmentPermanent("an enchantment")));
        // {2}{W}: Slumbering Keepguard gets +1/+1 until end of turn for each enchantment you control.
        this.addAbility(new SimpleActivatedAbility(
            new BoostSourceEffect(value, value, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{W}"))
            .addHint(new ValueHint("Enchantments you control", value))
        );
    }

    private SlumberingKeepguard(final SlumberingKeepguard card) {
        super(card);
    }

    @Override
    public SlumberingKeepguard copy() {
        return new SlumberingKeepguard(this);
    }
}
