
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class GiantAdephage extends CardImpl {

    public GiantAdephage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Giant Adephage deals combat damage to a player, create a token that is a copy of Giant Adephage.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenCopySourceEffect(), false));

    }

    private GiantAdephage(final GiantAdephage card) {
        super(card);
    }

    @Override
    public GiantAdephage copy() {
        return new GiantAdephage(this);
    }
}
