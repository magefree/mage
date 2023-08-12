package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToSourceByPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnchantedPredicate;

/**
 *
 * @author L_J
 */
public final class EnchantedBeing extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("enchanted creatures");

    static {
        filter.add(EnchantedPredicate.instance);
    }

    public EnchantedBeing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prevent all combat damage that would be dealt to Enchanted Being by enchanted creatures.
        this.addAbility(new SimpleStaticAbility(new PreventAllDamageToSourceByPermanentsEffect(filter, true)));
    }

    private EnchantedBeing(final EnchantedBeing card) {
        super(card);
    }

    @Override
    public EnchantedBeing copy() {
        return new EnchantedBeing(this);
    }
}
