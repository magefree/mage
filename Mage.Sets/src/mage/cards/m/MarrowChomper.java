
package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevouredCreaturesCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.DevourAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class MarrowChomper extends CardImpl {

    public MarrowChomper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{G}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.LIZARD);


        
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Devour 2 (As this enters the battlefield, you may sacrifice any number of creatures. This creature enters the battlefield with twice that many +1/+1 counters on it.)
        this.addAbility(new DevourAbility(2));

        // When Marrow Chomper enters the battlefield, you gain 2 life for each creature it devoured.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(new DevouredCreaturesCount(2))));
    }

    private MarrowChomper(final MarrowChomper card) {
        super(card);
    }

    @Override
    public MarrowChomper copy() {
        return new MarrowChomper(this);
    }
}
