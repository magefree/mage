
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author Backfir3
 */
public final class ArgothianEnchantress extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an Enchantment spell");

    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    public ArgothianEnchantress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        this.addAbility(ShroudAbility.getInstance());
        // Whenever you cast an Enchantment spell, you draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DrawCardSourceControllerEffect(1), filter, false));
    }

    public ArgothianEnchantress(final ArgothianEnchantress card) {
        super(card);
    }

    @Override
    public ArgothianEnchantress copy() {
        return new ArgothianEnchantress(this);
    }
}
