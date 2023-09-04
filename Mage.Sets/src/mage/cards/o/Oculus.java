

package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class Oculus extends CardImpl {

    public Oculus (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HOMUNCULUS);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1), true));
    }

    private Oculus(final Oculus card) {
        super(card);
    }

    @Override
    public Oculus copy() {
        return new Oculus(this);
    }

}
