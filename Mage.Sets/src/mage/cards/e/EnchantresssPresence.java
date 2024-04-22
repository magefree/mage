package mage.cards.e;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author Plopman
 */
public final class EnchantresssPresence extends CardImpl {

    public EnchantresssPresence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");


        // Whenever you cast an enchantment spell, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_SPELL_AN_ENCHANTMENT, false));
    }

    private EnchantresssPresence(final EnchantresssPresence card) {
        super(card);
    }

    @Override
    public EnchantresssPresence copy() {
        return new EnchantresssPresence(this);
    }
}
