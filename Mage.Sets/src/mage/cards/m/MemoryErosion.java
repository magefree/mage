package mage.cards.m;

import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class MemoryErosion extends CardImpl {

    public MemoryErosion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");

        // Whenever an opponent casts a spell, that player puts the top two cards of their library into their graveyard.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                Zone.BATTLEFIELD,
                new MillCardsTargetEffect(2),
                StaticFilters.FILTER_SPELL_A,
                false,
                SetTargetPointer.PLAYER
        ));
    }

    private MemoryErosion(final MemoryErosion card) {
        super(card);
    }

    @Override
    public MemoryErosion copy() {
        return new MemoryErosion(this);
    }
}
