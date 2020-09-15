
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class OnduGreathorn extends CardImpl {

    public OnduGreathorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // <i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, Ondu Greathorn gets +2/+2 until end of turn.
        this.addAbility(new LandfallAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn), false));
    }

    public OnduGreathorn(final OnduGreathorn card) {
        super(card);
    }

    @Override
    public OnduGreathorn copy() {
        return new OnduGreathorn(this);
    }
}
