
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author noxx (Harvester of Souls), cbt33
 */
public final class DoggedHunter extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature token");
    static {
        filter.add(TokenPredicate.TRUE);
    }

    public DoggedHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOMAD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Destroy target creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
        
    }

    private DoggedHunter(final DoggedHunter card) {
        super(card);
    }

    @Override
    public DoggedHunter copy() {
        return new DoggedHunter(this);
    }
}
