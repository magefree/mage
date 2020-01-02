package mage.cards.i;

import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.EnchantmentOrEnchantedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InspireAwe extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("enchanted creatures and enchantment creatures");

    static {
        filter.add(EnchantmentOrEnchantedPredicate.instance);
    }

    public InspireAwe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Prevent all combat damage that would be dealt this turn except by enchanted creatures and enchantment creatures. Scry 2.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(
                filter, Duration.EndOfTurn, true
        ));
        this.getSpellAbility().addEffect(new ScryEffect(2));
    }

    private InspireAwe(final InspireAwe card) {
        super(card);
    }

    @Override
    public InspireAwe copy() {
        return new InspireAwe(this);
    }
}
