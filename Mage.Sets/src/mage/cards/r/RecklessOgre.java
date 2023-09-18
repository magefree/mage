
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAloneSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LoneFox
 */
public final class RecklessOgre extends CardImpl {

    public RecklessOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.OGRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Reckless Ogre attacks alone, it gets +3/+0 until end of turn.
        this.addAbility(new AttacksAloneSourceTriggeredAbility(new BoostSourceEffect(3, 0, Duration.EndOfTurn).setText("it gets +3/+0 until end of turn")));
    }

    private RecklessOgre(final RecklessOgre card) {
        super(card);
    }

    @Override
    public RecklessOgre copy() {
        return new RecklessOgre(this);
    }
}
