package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByAllTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.permanent.token.KeimiToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TatsunariToadRider extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent();
    private static final FilterPermanent filter3 = new FilterControlledPermanent(SubType.FROG);

    static {
        filter.add(new NamePredicate("Keimi"));
        filter2.add(Predicates.or(
                new AbilityPredicate(FlyingAbility.class),
                new AbilityPredicate(ReachAbility.class)
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public TatsunariToadRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast an enchantment spell, if you don't control a creature named Keimi, create Keimi, a legendary 3/3 black and green Frog creature token with "Whenever you cast an enchantment spell, each opponent loses 1 life and you gain 1 life."
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new SpellCastControllerTriggeredAbility(
                        new CreateTokenEffect(new KeimiToken()),
                        StaticFilters.FILTER_SPELL_AN_ENCHANTMENT, false
                ), condition, "Whenever you cast an enchantment spell, if you don't control " +
                "a creature named Keimi, create Keimi, a legendary 3/3 black and green Frog creature token with " +
                "\"Whenever you cast an enchantment spell, each opponent loses 1 life and you gain 1 life.\""
        ));

        // {1}{G/U}: Tatsunari, Toad Rider and target Frog you control can't be blocked this turn except by creatures with flying or reach.
        Ability ability = new SimpleActivatedAbility(
                new CantBeBlockedByCreaturesSourceEffect(
                        filter2, Duration.EndOfTurn
                ).setText("{this}"), new ManaCostsImpl<>("{1}{G/U}")
        );
        ability.addEffect(new CantBeBlockedByAllTargetEffect(filter2, Duration.EndOfTurn)
                .setText("and target Frog you control can't be blocked this turn except by creatures with flying or reach"));
        ability.addTarget(new TargetPermanent(filter3));
        this.addAbility(ability);
    }

    private TatsunariToadRider(final TatsunariToadRider card) {
        super(card);
    }

    @Override
    public TatsunariToadRider copy() {
        return new TatsunariToadRider(this);
    }
}
