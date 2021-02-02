
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.RevealTopLandToBattlefieldElseHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mluds
 */
public final class CoilingOracle extends CardImpl {

    public CoilingOracle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{U}");
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
	
        // When Coiling Oracle enters the battlefield, reveal the top card of your library. If it's a land card, put it onto the battlefield. Otherwise, put that card into your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RevealTopLandToBattlefieldElseHandEffect()));
    }

    private CoilingOracle(final CoilingOracle card) {
        super(card);
    }

    @Override
    public CoilingOracle copy() {
        return new CoilingOracle(this);
    }
}
