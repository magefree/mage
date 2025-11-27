
package mage.cards.u;

import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.SkulkAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class UninvitedGeist extends TransformingDoubleFacedCard {

    public UninvitedGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "{2}{U}",
                "Unimpeded Trespasser",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "U");

        this.getLeftHalfCard().setPT(2, 2);
        this.getRightHalfCard().setPT(3, 3);

        // Skulk (This creature can't be blocked by creatures with greater power.)
        this.getLeftHalfCard().addAbility(new SkulkAbility());

        // When Uninvited Geist deals combat damage to a player, transform it.
        this.getLeftHalfCard().addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new TransformSourceEffect(), false));

        // Unimpeded Trespasser

        // Unimpeded Trespasser can't be blocked.
        this.getRightHalfCard().addAbility(new CantBeBlockedSourceAbility());
    }

    private UninvitedGeist(final UninvitedGeist card) {
        super(card);
    }

    @Override
    public UninvitedGeist copy() {
        return new UninvitedGeist(this);
    }
}
