package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.PayMoreToCastAsThoughtItHadFlashAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.FaerieRogueToken;

import java.util.UUID;

/**
 * @author karapuzz14
 */
public final class TegwyllsScouring extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(TappedPredicate.UNTAPPED);
    }

    public TegwyllsScouring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // You may cast Tegwyll's Scouring as though it had flash by tapping three untapped creatures you control with flying in addition to paying its other costs.
        CostsImpl<Cost> costs = new CostsImpl<>().setText("tapping three untapped creatures you control with flying");
        costs.add(new TapTargetCost(3, filter).setText(""));

        Ability ability = new PayMoreToCastAsThoughtItHadFlashAbility(this, costs);
        ability.addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
        ability.addEffect(new CreateTokenEffect(new FaerieRogueToken(), 3));
        this.addAbility(ability);

        // Destroy all creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));

        //Create three 1/1 black Faerie Rogue creature tokens with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new FaerieRogueToken(), 3));
    }

    private TegwyllsScouring(final TegwyllsScouring card) {
        super(card);
    }

    @Override
    public TegwyllsScouring copy() {
        return new TegwyllsScouring(this);
    }
}
