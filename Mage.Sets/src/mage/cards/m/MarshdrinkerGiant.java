
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class MarshdrinkerGiant extends CardImpl {
    
    private static final FilterLandPermanent filter = new FilterLandPermanent("Island or Swamp an opponent controls");
    
    static {
        filter.add(Predicates.or(
                SubType.ISLAND.getPredicate(),
                SubType.SWAMP.getPredicate()));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public MarshdrinkerGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Marshdrinker Giant enters the battlefield, destroy target Island or Swamp an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetLandPermanent(filter));
        this.addAbility(ability);
        
    }

    private MarshdrinkerGiant(final MarshdrinkerGiant card) {
        super(card);
    }

    @Override
    public MarshdrinkerGiant copy() {
        return new MarshdrinkerGiant(this);
    }
}
