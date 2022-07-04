
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class ViashinoSlaughtermaster extends CardImpl {

    public ViashinoSlaughtermaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(DoubleStrikeAbility.getInstance());
        // {B}{G}: Viashino Slaughtermaster gets +1/+1 until end of turn. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{B}{G}")));
    }

    private ViashinoSlaughtermaster(final ViashinoSlaughtermaster card) {
        super(card);
    }

    @Override
    public ViashinoSlaughtermaster copy() {
        return new ViashinoSlaughtermaster(this);
    }
}
