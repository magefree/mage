
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.abilities.keyword.ExtortAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetNonlandPermanent;

/**
*
* @author LevelX2
*/
public final class ThrullParasite extends CardImpl {

    public ThrullParasite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.THRULL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Extort
        this.addAbility(new ExtortAbility());

        // {tap}, Pay 2 life: Remove a counter from target nonland permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RemoveCounterTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetNonlandPermanent());
        ability.addCost(new PayLifeCost(2));
        this.addAbility(ability);
    }

    private ThrullParasite(final ThrullParasite card) {
        super(card);
    }

    @Override
    public ThrullParasite copy() {
        return new ThrullParasite(this);
    }
}
