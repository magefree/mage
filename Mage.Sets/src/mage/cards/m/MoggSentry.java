
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
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
public final class MoggSentry extends CardImpl {

    public MoggSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever an opponent casts a spell, Mogg Sentry gets +2/+2 until end of turn.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn), false));
    }

    private MoggSentry(final MoggSentry card) {
        super(card);
    }

    @Override
    public MoggSentry copy() {
        return new MoggSentry(this);
    }
}
