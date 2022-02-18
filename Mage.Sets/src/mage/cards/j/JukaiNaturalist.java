package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JukaiNaturalist extends CardImpl {

    private static final FilterCard filter = new FilterEnchantmentCard("enchantment spells");

    public JukaiNaturalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{G}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Enchantment spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private JukaiNaturalist(final JukaiNaturalist card) {
        super(card);
    }

    @Override
    public JukaiNaturalist copy() {
        return new JukaiNaturalist(this);
    }
}
