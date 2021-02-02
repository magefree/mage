
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.BrainstormEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class RiverwiseAugur extends CardImpl {

    public RiverwiseAugur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Riverwise Augur enters the battlefield, draw three cards, then put two cards from your hand on top of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BrainstormEffect()));
    }

    private RiverwiseAugur(final RiverwiseAugur card) {
        super(card);
    }

    @Override
    public RiverwiseAugur copy() {
        return new RiverwiseAugur(this);
    }
}
