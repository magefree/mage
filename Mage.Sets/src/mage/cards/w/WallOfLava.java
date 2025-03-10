
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author anonymous
 */
public final class WallOfLava extends CardImpl {

    public WallOfLava(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {R}: Wall of Lava gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));
    }

    private WallOfLava(final WallOfLava card) {
        super(card);
    }

    @Override
    public WallOfLava copy() {
        return new WallOfLava(this);
    }
}
