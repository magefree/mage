
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class BansheeOfTheDreadChoir extends CardImpl {

    public BansheeOfTheDreadChoir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Myriad
        this.addAbility(new MyriadAbility());
        // Whenever Banshee of the Dread Choir deals combat damage to a player, that player discards a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DiscardTargetEffect(1), false, true));
    }

    private BansheeOfTheDreadChoir(final BansheeOfTheDreadChoir card) {
        super(card);
    }

    @Override
    public BansheeOfTheDreadChoir copy() {
        return new BansheeOfTheDreadChoir(this);
    }
}
