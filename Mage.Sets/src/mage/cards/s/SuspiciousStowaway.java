package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuspiciousStowaway extends CardImpl {

    public SuspiciousStowaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.transformable = true;
        this.secondSideCardClazz = mage.cards.s.SeafaringWerewolf.class;

        // Suspicious Stowaway can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // Whenever Suspicious Stowaway deals combat damage to a player, draw a card, then discard a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1), false
        ));

        // Daybound
        this.addAbility(new TransformAbility());
        this.addAbility(new DayboundAbility());
    }

    private SuspiciousStowaway(final SuspiciousStowaway card) {
        super(card);
    }

    @Override
    public SuspiciousStowaway copy() {
        return new SuspiciousStowaway(this);
    }
}
