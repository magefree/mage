
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class ElderPineOfJukai extends CardImpl {

    public ElderPineOfJukai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast a Spirit or Arcane spell, reveal the top three cards of your library. Put all land cards revealed this way into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new SpellCastControllerTriggeredAbility(new RevealLibraryPutIntoHandEffect(3, StaticFilters.FILTER_CARD_LANDS, Zone.LIBRARY), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false));

        // Soulshift 2
        this.addAbility(new SoulshiftAbility(2));
    }

    private ElderPineOfJukai(final ElderPineOfJukai card) {
        super(card);
    }

    @Override
    public ElderPineOfJukai copy() {
        return new ElderPineOfJukai(this);
    }
}
