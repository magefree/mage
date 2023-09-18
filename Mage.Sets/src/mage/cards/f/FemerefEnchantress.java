
package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class FemerefEnchantress extends CardImpl {

    public FemerefEnchantress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever an enchantment is put into a graveyard from the battlefield, draw a card.
        this.addAbility(new ZoneChangeAllTriggeredAbility(Zone.BATTLEFIELD, Zone.BATTLEFIELD, Zone.GRAVEYARD,
                new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_PERMANENT_ENCHANTMENT,
                "Whenever an enchantment is put into a graveyard from the battlefield, ", false));
    }

    private FemerefEnchantress(final FemerefEnchantress card) {
        super(card);
    }

    @Override
    public FemerefEnchantress copy() {
        return new FemerefEnchantress(this);
    }
}
