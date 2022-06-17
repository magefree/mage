

package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class ScepterOfDominance extends CardImpl {

    public ScepterOfDominance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}{W}{W}");


        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private ScepterOfDominance(ScepterOfDominance card) {
        super(card);
    }

    @Override
    public ScepterOfDominance copy() {
        return new ScepterOfDominance(this);
    }

}
