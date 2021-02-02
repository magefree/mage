
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
*
* @author LevelX2
*/
public final class ShardlessAgent extends CardImpl {

    public ShardlessAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}{G}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Cascade
        this.addAbility(new CascadeAbility());
    }

    private ShardlessAgent(final ShardlessAgent card) {
        super(card);
    }

    @Override
    public ShardlessAgent copy() {
        return new ShardlessAgent(this);
    }
}
