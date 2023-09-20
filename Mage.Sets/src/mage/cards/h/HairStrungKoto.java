package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX
 */
public final class HairStrungKoto extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

        public HairStrungKoto (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        /* Tap an untapped creature you control: Target player puts the top 
         * card of their library into their graveyard.
         */
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new MillCardsTargetEffect(1),
                new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, false)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private HairStrungKoto(final HairStrungKoto card) {
        super(card);
    }

    @Override
    public HairStrungKoto copy() {
        return new HairStrungKoto(this);
    }

}
