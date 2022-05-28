
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.SwitchPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author jeffwadsworth

 */
public final class CragPuca extends CardImpl {

    public CragPuca(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U/R}{U/R}{U/R}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {UR}: Switch Crag Puca's power and toughness until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SwitchPowerToughnessSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{U/R}")));
        
    }

    private CragPuca(final CragPuca card) {
        super(card);
    }

    @Override
    public CragPuca copy() {
        return new CragPuca(this);
    }
}
