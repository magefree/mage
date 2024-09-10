

package mage.cards.p;

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
 * @author Loki
 */
public final class PlatedGeopede extends CardImpl {

    public PlatedGeopede (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(new LandfallAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn), false));
    }

    private PlatedGeopede(final PlatedGeopede card) {
        super(card);
    }

    @Override
    public PlatedGeopede copy() {
        return new PlatedGeopede(this);
    }

}
