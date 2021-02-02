
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.SwitchPowerToughnessSourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author North
 */
public final class CalciteSnapper extends CardImpl {

    public CalciteSnapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.TURTLE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        this.addAbility(ShroudAbility.getInstance());
        this.addAbility(new LandfallAbility(new SwitchPowerToughnessSourceEffect(Duration.EndOfTurn), true));
    }

    private CalciteSnapper(final CalciteSnapper card) {
        super(card);
    }

    @Override
    public CalciteSnapper copy() {
        return new CalciteSnapper(this);
    }
}
