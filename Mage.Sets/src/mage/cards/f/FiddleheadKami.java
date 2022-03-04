
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class FiddleheadKami extends CardImpl {

    public FiddleheadKami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a Spirit or Arcane spell, regenerate Fiddlehead Kami.
        this.addAbility(new SpellCastControllerTriggeredAbility(new RegenerateSourceEffect(), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false));
    }

    private FiddleheadKami(final FiddleheadKami card) {
        super(card);
    }

    @Override
    public FiddleheadKami copy() {
        return new FiddleheadKami(this);
    }
}
