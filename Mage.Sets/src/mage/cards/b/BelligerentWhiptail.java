
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class BelligerentWhiptail extends CardImpl {

    public BelligerentWhiptail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // <i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, Belligerent Whiptail gains first strike until end of turn.
        this.addAbility(new LandfallAbility(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), false));

    }

    public BelligerentWhiptail(final BelligerentWhiptail card) {
        super(card);
    }

    @Override
    public BelligerentWhiptail copy() {
        return new BelligerentWhiptail(this);
    }
}
