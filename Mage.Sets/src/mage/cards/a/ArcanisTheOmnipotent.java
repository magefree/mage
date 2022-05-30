
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class ArcanisTheOmnipotent extends CardImpl {

    public ArcanisTheOmnipotent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        
        // Tap: Draw three cards.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(3), new TapSourceCost()));
        
        // {2}{U}{U}: Return Arcanis the Omnipotent to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new ManaCostsImpl<>("{2}{U}{U}")));
    }

    private ArcanisTheOmnipotent(final ArcanisTheOmnipotent card) {
        super(card);
    }

    @Override
    public ArcanisTheOmnipotent copy() {
        return new ArcanisTheOmnipotent(this);
    }
}
