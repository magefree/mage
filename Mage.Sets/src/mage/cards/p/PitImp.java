
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class PitImp extends CardImpl {

    public PitImp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.IMP);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {B}: Pit Imp gets +1/+0 until end of turn. Activate this ability no more than twice each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1,0,Duration.EndOfTurn), new ManaCostsImpl<>("{B}"), 2));
    }

    private PitImp(final PitImp card) {
        super(card);
    }

    @Override
    public PitImp copy() {
        return new PitImp(this);
    }
}
