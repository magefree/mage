package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyBlessedSamurai extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            StaticFilters.FILTER_CONTROLLED_PERMANENT_ENCHANTMENT
    );
    private static final Hint hint = new ValueHint("Enchantments you control", xValue);

    public SkyBlessedSamurai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{6}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // This spell costs {1} less to cast for each enchantment you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new SpellCostReductionForEachSourceEffect(1, xValue)
        ).addHint(hint));

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private SkyBlessedSamurai(final SkyBlessedSamurai card) {
        super(card);
    }

    @Override
    public SkyBlessedSamurai copy() {
        return new SkyBlessedSamurai(this);
    }
}
