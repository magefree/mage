
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author ilcartographer
 */
public final class Onulet extends CardImpl {

    public Onulet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Onulet dies, you gain 2 life.
        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(2)));
    }

    private Onulet(final Onulet card) {
        super(card);
    }

    @Override
    public Onulet copy() {
        return new Onulet(this);
    }
}
