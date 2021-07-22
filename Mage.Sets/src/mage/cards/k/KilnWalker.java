
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author North
 */
public final class KilnWalker extends CardImpl {

    public KilnWalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(3, 0, Duration.EndOfTurn), false));
    }

    private KilnWalker(final KilnWalker card) {
        super(card);
    }

    @Override
    public KilnWalker copy() {
        return new KilnWalker(this);
    }
}
