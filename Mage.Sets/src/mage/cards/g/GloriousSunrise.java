package mage.cards.g;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GloriousSunrise extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(
            condition, "You control a creature with power 3 or greater"
    );

    public GloriousSunrise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");

        // At the beginning of combat on your turn, choose one —
        // • Creatures you control get +1/+1 and gain trample until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BoostControlledEffect(
                1, 1, Duration.EndOfTurn
        ).setText("reatures you control get +1/+1"), TargetController.YOU, false);
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and gain trample until end of turn"));

        // • Target land gains "{T}: Add {G}{G}{G}" until end of turn.
        Mode mode = new Mode(new GainAbilityTargetEffect(new SimpleManaAbility(
                Zone.BATTLEFIELD, Mana.GreenMana(3), new TapSourceCost()
        ), Duration.EndOfTurn));
        mode.addTarget(new TargetLandPermanent());
        ability.addMode(mode);

        // • Draw a card if you control a creature with power 3 or greater.
        ability.addMode(new Mode(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), condition,
                "draw a card if you control a creature with power 3 or greater"
        )));

        // • You gain 3 life.
        ability.addMode(new Mode(new GainLifeEffect(3)));
        this.addAbility(ability.addHint(hint));
    }

    private GloriousSunrise(final GloriousSunrise card) {
        super(card);
    }

    @Override
    public GloriousSunrise copy() {
        return new GloriousSunrise(this);
    }
}
