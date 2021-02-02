
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class BurningTreeEmissary extends CardImpl {

    public BurningTreeEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R/G}{R/G}");
        this.subtype.add(SubType.HUMAN, SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Burning-Tree Emissary enters the battlefield, add {R}{G}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BasicManaEffect(new Mana(0, 0, 0, 1,1, 0,0, 0))));
    }

    private BurningTreeEmissary(final BurningTreeEmissary card) {
        super(card);
    }

    @Override
    public BurningTreeEmissary copy() {
        return new BurningTreeEmissary(this);
    }
}
