package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConclaveEvangelist extends CardImpl {

    public ConclaveEvangelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G/W}{G/W}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Myriad
        this.addAbility(new MyriadAbility());

        // Whenever Conclave Evangelist deals combat damage to a player, create a token that's a copy of Conclave Evangelist.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenCopySourceEffect(), false));
    }

    private ConclaveEvangelist(final ConclaveEvangelist card) {
        super(card);
    }

    @Override
    public ConclaveEvangelist copy() {
        return new ConclaveEvangelist(this);
    }
}
