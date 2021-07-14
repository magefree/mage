
package mage.cards.h;

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
 * @author Loki
 */
public final class HollowDogs extends CardImpl {

    public HollowDogs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Hollow Dogs attacks, it gets +2/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn), false));
    }

    private HollowDogs(final HollowDogs card) {
        super(card);
    }

    @Override
    public HollowDogs copy() {
        return new HollowDogs(this);
    }
}
