
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class JeskaiWindscout extends CardImpl {

    public JeskaiWindscout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Prowess <em>(Whenever you cast a noncreature spell, this creature gets +1/+1 until end of turn.)</em>
        this.addAbility(new ProwessAbility());
    }

    private JeskaiWindscout(final JeskaiWindscout card) {
        super(card);
    }

    @Override
    public JeskaiWindscout copy() {
        return new JeskaiWindscout(this);
    }
}
