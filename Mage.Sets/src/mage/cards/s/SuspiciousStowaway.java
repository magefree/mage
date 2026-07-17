package mage.cards.s;

import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuspiciousStowaway extends TransformingDoubleFacedCard {

    public SuspiciousStowaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ROGUE, SubType.WEREWOLF}, "{1}{U}",
                "Seafaring Werewolf",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Suspicious Stowaway
        this.getLeftHalfCard().setPT(1, 1);

        // Suspicious Stowaway can't be blocked.
        this.getLeftHalfCard().addAbility(new CantBeBlockedSourceAbility());

        // Whenever Suspicious Stowaway deals combat damage to a player, draw a card, then discard a card.
        this.getLeftHalfCard().addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1), false
        ));

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Seafaring Werewolf
        this.getRightHalfCard().setPT(2, 1);

        // Seafaring Werewolf can't be blocked.
        this.getRightHalfCard().addAbility(new CantBeBlockedSourceAbility());

        // Whenever Seafaring Werewolf deals combat damage to a player, draw a card.
        this.getRightHalfCard().addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        ));

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private SuspiciousStowaway(final SuspiciousStowaway card) {
        super(card);
    }

    @Override
    public SuspiciousStowaway copy() {
        return new SuspiciousStowaway(this);
    }
}
