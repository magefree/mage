
package mage.cards.l;

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
 * @author fireshoes
 */
public final class LurkingNightstalker extends CardImpl {

    public LurkingNightstalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}");
        this.subtype.add(SubType.NIGHTSTALKER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Lurking Nightstalker attacks, it gets +2/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn), false));
    }

    private LurkingNightstalker(final LurkingNightstalker card) {
        super(card);
    }

    @Override
    public LurkingNightstalker copy() {
        return new LurkingNightstalker(this);
    }
}
