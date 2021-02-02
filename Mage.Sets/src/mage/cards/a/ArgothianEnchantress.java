package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;

import java.util.UUID;

/**
 * @author Backfir3
 */
public final class ArgothianEnchantress extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an Enchantment spell");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public ArgothianEnchantress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Shroud
        this.addAbility(ShroudAbility.getInstance());

        // Whenever you cast an Enchantment spell, you draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DrawCardSourceControllerEffect(1, "you"), filter, false));
    }

    private ArgothianEnchantress(final ArgothianEnchantress card) {
        super(card);
    }

    @Override
    public ArgothianEnchantress copy() {
        return new ArgothianEnchantress(this);
    }
}
