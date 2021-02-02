
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class UtvaraScalper extends CardImpl {

    public UtvaraScalper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Utvara Scalper attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private UtvaraScalper(final UtvaraScalper card) {
        super(card);
    }

    @Override
    public UtvaraScalper copy() {
        return new UtvaraScalper(this);
    }
}
