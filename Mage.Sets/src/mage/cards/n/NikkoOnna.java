
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetEnchantmentPermanent;

/**
 * @author Loki
 */
public final class NikkoOnna extends CardImpl {

    public NikkoOnna(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Nikko-Onna enters the battlefield, destroy target enchantment.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability);

        // Whenever you cast a Spirit or Arcane spell, you may return Nikko-Onna to its owner's hand.
        this.addAbility(new SpellCastControllerTriggeredAbility(new ReturnToHandSourceEffect(true), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true));
    }

    private NikkoOnna(final NikkoOnna card) {
        super(card);
    }

    @Override
    public NikkoOnna copy() {
        return new NikkoOnna(this);
    }
}
