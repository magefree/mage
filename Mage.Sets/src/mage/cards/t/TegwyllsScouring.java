package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.FaerieRogueToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author karapuzz14
 */
public final class TegwyllsScouring extends CardImpl {

    public TegwyllsScouring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");


        // You may cast Tegwyll's Scouring as though it had flash by tapping three untapped creatures you control with flying in addition to paying its other costs.
        Ability ability = new TegwyllsScouringCastAsThoughtItHadFlashAbility(this);
        this.addAbility(ability.setRuleAtTheTop(true));

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

class TegwyllsScouringCastAsThoughtItHadFlashAbility extends SpellAbility {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(TappedPredicate.UNTAPPED);
    }

    private final Cost costsToAdd;

    public TegwyllsScouringCastAsThoughtItHadFlashAbility(Card card) {
        super(card.getSpellAbility().getManaCosts().copy(), card.getName(), Zone.HAND, SpellAbilityType.BASE_ALTERNATE);

        Cost asThoughCost = new TapTargetCost(new TargetControlledCreaturePermanent(3, 3, filter, true)).setText("");
        CostsImpl<Cost> costs = new CostsImpl<>().setText("tapping three untapped creatures you control with flying");
        costs.add(asThoughCost);
        this.costsToAdd = costs;
        this.addCost(costsToAdd);

        this.addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
        this.addEffect(new CreateTokenEffect(new FaerieRogueToken(), 3));
        this.timing = TimingRule.INSTANT;
    }

    protected TegwyllsScouringCastAsThoughtItHadFlashAbility(final TegwyllsScouringCastAsThoughtItHadFlashAbility ability) {
        super(ability);
        this.costsToAdd = ability.costsToAdd;
    }

    @Override
    public TegwyllsScouringCastAsThoughtItHadFlashAbility copy() {
        return new TegwyllsScouringCastAsThoughtItHadFlashAbility(this);
    }

    @Override
    public String getRule() {
        return "You may cast {this} as though it had flash by " + costsToAdd.getText() + " in addition to paying its other costs.";
    }

}
