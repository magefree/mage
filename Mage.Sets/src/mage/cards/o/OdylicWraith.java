
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class OdylicWraith extends CardImpl {

    public OdylicWraith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.WRAITH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());
        // Whenever Odylic Wraith deals damage to a player, that player discards a card.
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(new DiscardTargetEffect(1), false, true));
    }

    private OdylicWraith(final OdylicWraith card) {
        super(card);
    }

    @Override
    public OdylicWraith copy() {
        return new OdylicWraith(this);
    }
}
