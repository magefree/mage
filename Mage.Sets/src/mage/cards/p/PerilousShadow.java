
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class PerilousShadow extends CardImpl {

    public PerilousShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.SHADE);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);


        // {1}{B}: Perilous Shadow gets +2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2,2, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{B}")));

    }

    private PerilousShadow(final PerilousShadow card) {
        super(card);
    }

    @Override
    public PerilousShadow copy() {
        return new PerilousShadow(this);
    }
}
