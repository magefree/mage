
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author dustinconrad
 */
public final class ForcedFruition extends CardImpl {

    public ForcedFruition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{U}{U}");


        // Whenever an opponent casts a spell, that player draws seven cards.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new DrawCardTargetEffect(7),
                StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.PLAYER));
    }

    private ForcedFruition(final ForcedFruition card) {
        super(card);
    }

    @Override
    public ForcedFruition copy() {
        return new ForcedFruition(this);
    }
}
