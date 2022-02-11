package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.game.permanent.token.DragonSpiritToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoroGoroDiscipleOfRyusei extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("if you control an attacking modified creature");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(ModifiedPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(
            condition, "You control and attacking modified creature"
    );

    public GoroGoroDiscipleOfRyusei(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {R}: Creatures you control gain haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilityAllEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ), new ManaCostsImpl<>("{R}")));

        // {3}{R}{R}: Create a 5/5 red Dragon Spirit creature token with flying. Activate only if you control an attacking modified creature.
        this.addAbility(new ConditionalActivatedAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new DragonSpiritToken()),
                new ManaCostsImpl<>("{3}{R}{R}"), condition
        ).addHint(hint));
    }

    private GoroGoroDiscipleOfRyusei(final GoroGoroDiscipleOfRyusei card) {
        super(card);
    }

    @Override
    public GoroGoroDiscipleOfRyusei copy() {
        return new GoroGoroDiscipleOfRyusei(this);
    }
}
