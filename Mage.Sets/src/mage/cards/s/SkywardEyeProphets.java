
package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.RevealTopLandToBattlefieldElseHandEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author North
 */
public final class SkywardEyeProphets extends CardImpl {

    public SkywardEyeProphets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{W}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // {tap}: Reveal the top card of your library. If it's a land card, put it onto the battlefield. Otherwise, put it into your hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RevealTopLandToBattlefieldElseHandEffect("it"), new TapSourceCost()));
    }

    private SkywardEyeProphets(final SkywardEyeProphets card) {
        super(card);
    }

    @Override
    public SkywardEyeProphets copy() {
        return new SkywardEyeProphets(this);
    }
}
