
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33
 */
public final class ZombieAssassin extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature");    
    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }
    
    

    public ZombieAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.ZOMBIE, SubType.ASSASSIN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {tap}, Exile two cards from your graveyard and Zombie Assassin: Destroy target nonblack creature. It can't be regenerated.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(true), new TapSourceCost());
        Target target = new TargetCreaturePermanent(filter);
        ability.addTarget(target);
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2,2,new FilterCard("cards from your graveyard"))));
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    public ZombieAssassin(final ZombieAssassin card) {
        super(card);
    }

    @Override
    public ZombieAssassin copy() {
        return new ZombieAssassin(this);
    }
}
