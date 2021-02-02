
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
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
public final class SlashingTiger extends CardImpl {

    public SlashingTiger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Slashing Tiger becomes blocked, it gets +2/+2 until end of turn.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn), false));
    }

    private SlashingTiger(final SlashingTiger card) {
        super(card);
    }

    @Override
    public SlashingTiger copy() {
        return new SlashingTiger(this);
    }
}
