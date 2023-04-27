package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LivingTwister extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledLandPermanent("tapped land you control");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public LivingTwister(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // {1}{R}, Discard a land card: Living Twister deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(2), new ManaCostsImpl<>("{1}{R}")
        );
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_LAND_A)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // {G}: Return a tapped land you control to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(
                new ReturnToHandChosenControlledPermanentEffect(filter), new ManaCostsImpl<>("{G}")
        ));
    }

    private LivingTwister(final LivingTwister card) {
        super(card);
    }

    @Override
    public LivingTwister copy() {
        return new LivingTwister(this);
    }
}
