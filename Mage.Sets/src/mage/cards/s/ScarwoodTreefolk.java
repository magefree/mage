
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.TapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class ScarwoodTreefolk extends CardImpl {

    public ScarwoodTreefolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Scarwood Treefolk enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldAbility(new TapSourceEffect(), "tapped"));
    }

    private ScarwoodTreefolk(final ScarwoodTreefolk card) {
        super(card);
    }

    @Override
    public ScarwoodTreefolk copy() {
        return new ScarwoodTreefolk(this);
    }
}
