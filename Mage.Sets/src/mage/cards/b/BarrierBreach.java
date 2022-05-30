package mage.cards.b;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarrierBreach extends CardImpl {

    public BarrierBreach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Exile up to three target enchantments.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent(0, 3,
                new FilterEnchantmentPermanent("enchantments"), false));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private BarrierBreach(final BarrierBreach card) {
        super(card);
    }

    @Override
    public BarrierBreach copy() {
        return new BarrierBreach(this);
    }
}
