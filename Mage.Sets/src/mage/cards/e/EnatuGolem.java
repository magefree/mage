
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class EnatuGolem extends CardImpl {

    public EnatuGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        this.addAbility(new DiesTriggeredAbility(new GainLifeEffect(4), false));
    }

    public EnatuGolem(final EnatuGolem card) {
        super(card);
    }

    @Override
    public EnatuGolem copy() {
        return new EnatuGolem(this);
    }
}
