
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author cbt33
 */
public final class TreetopSentinel extends CardImpl {
    
    public TreetopSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // protection from green
        this.addAbility(ProtectionAbility.from(ObjectColor.GREEN));
    }

    private TreetopSentinel(final TreetopSentinel card) {
        super(card);
    }

    @Override
    public TreetopSentinel copy() {
        return new TreetopSentinel(this);
    }
}
