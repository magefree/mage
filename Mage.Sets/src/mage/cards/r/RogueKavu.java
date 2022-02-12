
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
public final class RogueKavu extends CardImpl {

    public RogueKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Rogue Kavu attacks alone, it gets +2/+0 until end of turn.
        this.addAbility(new AttacksAloneSourceTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn)));
    }

    private RogueKavu(final RogueKavu card) {
        super(card);
    }

    @Override
    public RogueKavu copy() {
        return new RogueKavu(this);
    }
}
