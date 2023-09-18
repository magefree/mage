
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class LoamDweller extends CardImpl {

    public LoamDweller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a Spirit or Arcane spell, you may put a land card from your hand onto the battlefield tapped.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A, false, true),
                StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true));
    }

    private LoamDweller(final LoamDweller card) {
        super(card);
    }

    @Override
    public LoamDweller copy() {
        return new LoamDweller(this);
    }
}
