
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author LoneFox

 */
public final class TeferisCare extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an enchantment");
    private static final FilterSpell filter2 = new FilterSpell("enchantment spell");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
        filter2.add(CardType.ENCHANTMENT.getPredicate());
    }

    public TeferisCare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");

        // {W}, Sacrifice an enchantment: Destroy target enchantment.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{W}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filter, true)));
        ability.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability);
        // {3}{U}{U}: Counter target enchantment spell.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ManaCostsImpl<>("{3}{U}{U}"));
        ability.addTarget(new TargetSpell(filter2));
        this.addAbility(ability);
    }

    private TeferisCare(final TeferisCare card) {
        super(card);
    }

    @Override
    public TeferisCare copy() {
        return new TeferisCare(this);
    }
}
