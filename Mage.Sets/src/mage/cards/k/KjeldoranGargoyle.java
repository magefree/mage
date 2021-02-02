
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageGainLifeSourceTriggeredAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class KjeldoranGargoyle extends CardImpl {

    public KjeldoranGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}");
        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        
        // Whenever Kjeldoran Gargoyle deals damage, you gain that much life.
        this.addAbility(new DealsDamageGainLifeSourceTriggeredAbility());
    }

    private KjeldoranGargoyle(final KjeldoranGargoyle card) {
        super(card);
    }

    @Override
    public KjeldoranGargoyle copy() {
        return new KjeldoranGargoyle(this);
    }
}