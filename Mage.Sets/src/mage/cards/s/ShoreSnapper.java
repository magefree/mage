
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class ShoreSnapper extends CardImpl {

    public ShoreSnapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(new IslandwalkAbility(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{U}")));
    }

    private ShoreSnapper(final ShoreSnapper card) {
        super(card);
    }

    @Override
    public ShoreSnapper copy() {
        return new ShoreSnapper(this);
    }
}
