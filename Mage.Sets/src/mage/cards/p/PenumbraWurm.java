
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.PenumbraWurmToken;

/**
 *
 * @author Loki
 */
public final class PenumbraWurm extends CardImpl {

    public PenumbraWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Penumbra Wurm dies, create a 6/6 black Wurm creature token with trample.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new PenumbraWurmToken(), 1), false));
    }

    private PenumbraWurm(final PenumbraWurm card) {
        super(card);
    }

    @Override
    public PenumbraWurm copy() {
        return new PenumbraWurm(this);
    }
}
