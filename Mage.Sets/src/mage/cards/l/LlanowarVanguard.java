
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class LlanowarVanguard extends CardImpl {

    public LlanowarVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Llanowar Vanguard gets +0/+4 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
            new BoostSourceEffect(0, 4, Duration.EndOfTurn), new TapSourceCost()));
    }

    private LlanowarVanguard(final LlanowarVanguard card) {
        super(card);
    }

    @Override
    public LlanowarVanguard copy() {
        return new LlanowarVanguard(this);
    }
}
