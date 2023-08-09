
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class PhantasmalBear extends CardImpl {

    public PhantasmalBear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Phantasmal Bear becomes the target of a spell or ability, sacrifice it.
        this.addAbility(new SourceBecomesTargetTriggeredAbility(new SacrificeSourceEffect()));
    }

    private PhantasmalBear(final PhantasmalBear card) {
        super(card);
    }

    @Override
    public PhantasmalBear copy() {
        return new PhantasmalBear(this);
    }
}
