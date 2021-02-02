
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
 * @author fireshoes
 */
public final class SandcrafterMage extends CardImpl {

    public SandcrafterMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Sandcrafter Mage enters the battlefield, bolster 1
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BolsterEffect(1), false));
    }

    private SandcrafterMage(final SandcrafterMage card) {
        super(card);
    }

    @Override
    public SandcrafterMage copy() {
        return new SandcrafterMage(this);
    }
}
