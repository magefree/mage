
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CatapultMaster extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Soldiers you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.SOLDIER.getPredicate());
    }
    public CatapultMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Tap five untapped Soldiers you control: Exile target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new TapTargetCost(new TargetControlledPermanent(5,5,filter,false)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CatapultMaster(final CatapultMaster card) {
        super(card);
    }

    @Override
    public CatapultMaster copy() {
        return new CatapultMaster(this);
    }
}
