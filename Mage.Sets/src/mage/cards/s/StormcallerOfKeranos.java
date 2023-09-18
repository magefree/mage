
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class StormcallerOfKeranos extends CardImpl {

    public StormcallerOfKeranos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // {1}{U}: Scry 1.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryEffect(1), new ManaCostsImpl<>("{1}{U}")));
    }

    private StormcallerOfKeranos(final StormcallerOfKeranos card) {
        super(card);
    }

    @Override
    public StormcallerOfKeranos copy() {
        return new StormcallerOfKeranos(this);
    }
}
