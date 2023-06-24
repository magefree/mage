
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
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
public final class WydwenTheBitingGale extends CardImpl {

    public WydwenTheBitingGale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);


        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(FlashAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());
        
        // {U}{B}, Pay 1 life: Return Wydwen, the Biting Gale to its owner's hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new ManaCostsImpl<>("{U}{B}"));
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private WydwenTheBitingGale(final WydwenTheBitingGale card) {
        super(card);
    }

    @Override
    public WydwenTheBitingGale copy() {
        return new WydwenTheBitingGale(this);
    }
}
