
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SandsteppeScavenger extends CardImpl {

    public SandsteppeScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Sandsteppe Scavenger enters the battlefield, bolster 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BolsterEffect(2), false));
    }

    private SandsteppeScavenger(final SandsteppeScavenger card) {
        super(card);
    }

    @Override
    public SandsteppeScavenger copy() {
        return new SandsteppeScavenger(this);
    }
}
