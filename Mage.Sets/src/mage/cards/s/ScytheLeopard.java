
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
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
public final class ScytheLeopard extends CardImpl {

    public ScytheLeopard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // <i>Landfall</i>-Whenever a land enters the battlefield under your control, Scythe Leopard gets +1/+1 until end of turn.
        this.addAbility(new LandfallAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), false));
    }

    private ScytheLeopard(final ScytheLeopard card) {
        super(card);
    }

    @Override
    public ScytheLeopard copy() {
        return new ScytheLeopard(this);
    }
}
