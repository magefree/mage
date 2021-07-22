package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Shacklegeist extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("untapped Spirits you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public Shacklegeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Shacklegeist can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());

        // Tap two untapped Spirits you control: Tap target creature you don't control.
        Ability ability = new SimpleActivatedAbility(
                new TapTargetEffect(), new TapTargetCost(new TargetControlledPermanent(2, filter))
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);
    }

    private Shacklegeist(final Shacklegeist card) {
        super(card);
    }

    @Override
    public Shacklegeist copy() {
        return new Shacklegeist(this);
    }
}
