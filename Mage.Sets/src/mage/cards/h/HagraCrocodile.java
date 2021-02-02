
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author North
 */
public final class HagraCrocodile extends CardImpl {

    public HagraCrocodile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.CROCODILE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        this.addAbility(new CantBlockAbility());
        this.addAbility(new LandfallAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn), false));
    }

    private HagraCrocodile(final HagraCrocodile card) {
        super(card);
    }

    @Override
    public HagraCrocodile copy() {
        return new HagraCrocodile(this);
    }
}
