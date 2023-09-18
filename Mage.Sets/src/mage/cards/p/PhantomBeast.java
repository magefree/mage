

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
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public final class PhantomBeast extends CardImpl {

    public PhantomBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        this.addAbility(new SourceBecomesTargetTriggeredAbility(new SacrificeSourceEffect()));
    }

    private PhantomBeast(final PhantomBeast card) {
        super(card);
    }

    @Override
    public PhantomBeast copy() {
        return new PhantomBeast(this);
    }
}
