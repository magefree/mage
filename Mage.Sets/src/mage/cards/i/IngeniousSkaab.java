
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class IngeniousSkaab extends CardImpl {

    public IngeniousSkaab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Prowess
        this.addAbility(new ProwessAbility());

        // {U}: Ingenius Skaab gets +1/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, -1, Duration.EndOfTurn), new ManaCostsImpl<>("{U}")));
    }

    private IngeniousSkaab(final IngeniousSkaab card) {
        super(card);
    }

    @Override
    public IngeniousSkaab copy() {
        return new IngeniousSkaab(this);
    }
}
