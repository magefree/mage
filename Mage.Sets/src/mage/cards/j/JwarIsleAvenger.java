
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SurgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class JwarIsleAvenger extends CardImpl {

    public JwarIsleAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Surge {2}{U} (You may cast this spell for its surge cost if you or a teammate has cast another spell this turn.)
        addAbility(new SurgeAbility(this, "{2}{U}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private JwarIsleAvenger(final JwarIsleAvenger card) {
        super(card);
    }

    @Override
    public JwarIsleAvenger copy() {
        return new JwarIsleAvenger(this);
    }
}
