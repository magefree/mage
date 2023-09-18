package mage.cards.r;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class RevelsongHorn extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("an untapped creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public RevelsongHorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {1}, {tap}, Tap an untapped creature you control: Target creature gets +1/+1 until end of turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(
                new BoostTargetEffect(1, 1, Duration.EndOfTurn), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RevelsongHorn(final RevelsongHorn card) {
        super(card);
    }

    @Override
    public RevelsongHorn copy() {
        return new RevelsongHorn(this);
    }
}
