
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class BalothWoodcrasher extends CardImpl {

    public BalothWoodcrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        LandfallAbility ability = new LandfallAbility(new BoostSourceEffect(4, 4, Duration.EndOfTurn).setText("{this} gets +4/+4"), false);
        ability.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn).setText("and gains trample until end of turn"));
        this.addAbility(ability);
    }

    private BalothWoodcrasher(final BalothWoodcrasher card) {
        super(card);
    }

    @Override
    public BalothWoodcrasher copy() {
        return new BalothWoodcrasher(this);
    }
}
