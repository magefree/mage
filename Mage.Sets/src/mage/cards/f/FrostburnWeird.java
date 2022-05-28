
package mage.cards.f;

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
public final class FrostburnWeird extends CardImpl {
    
    public FrostburnWeird(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U/R}{U/R}");
        this.subtype.add(SubType.WEIRD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {U/R}: Frostburn Weird gets +1/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, -1, Duration.EndOfTurn), new ManaCostsImpl<>("{U/R}")));
    }

    private FrostburnWeird(final FrostburnWeird card) {
        super(card);
    }

    @Override
    public FrostburnWeird copy() {
        return new FrostburnWeird(this);
    }
}
