package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RangersHawk extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("another untapped creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TappedPredicate.UNTAPPED);
    }

    public RangersHawk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {3}, {T}, Tap another untapped creature you control: Venture into the dungeon. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new VentureIntoTheDungeonEffect(), new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private RangersHawk(final RangersHawk card) {
        super(card);
    }

    @Override
    public RangersHawk copy() {
        return new RangersHawk(this);
    }
}
