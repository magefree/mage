package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.PayMoreToCastAsThoughtItHadFlashAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
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
import mage.game.permanent.token.FaerieRogueToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Xanderhall
 */
public final class TegwyllsScouring extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creatures with flying");
    private static final Costs<Cost> asThoughCost = new CostsImpl<>();

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));

        asThoughCost.add(new TapTargetCost(new TargetControlledCreaturePermanent(3, 3, filter, true)));
        asThoughCost.setText("tapping three creatures you control with flying");
    }

    public TegwyllsScouring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");
        
        // You may cast Tegwyll's Scouring as though it had flash by tapping three untapped creatures you control with flying in addition to paying its other costs.
        Ability ability = new PayMoreToCastAsThoughtItHadFlashAbility(this, asThoughCost);
        ability.addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
        ability.addEffect(new CreateTokenEffect(new FaerieRogueToken(), 3));
        this.addAbility(ability.setRuleAtTheTop(true));

        // Destroy all creatures. Create three 1/1 black Faerie Rogue creature tokens with flying.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
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
