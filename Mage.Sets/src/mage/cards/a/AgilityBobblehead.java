package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AgilityBobblehead extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.BOBBLEHEAD, "Bobbleheads you control"), null
    );
    private static final Hint hint = new ValueHint("Bobbleheads you control", xValue);
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(new AbilityPredicate(HasteAbility.class)));
    }

    public AgilityBobblehead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.BOBBLEHEAD);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {3}, {T}: Up to X target creatures you control each gain haste until end of turn and can't be blocked this turn except by creatures with haste, where X is the number of Bobbleheads you control as you activate this ability.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(HasteAbility.getInstance())
                        .setText("Up to X target creatures you control each gain haste until end of turn"),
                new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new CantBeBlockedTargetEffect(filter, Duration.EndOfTurn)
                .setText("and can't be blocked this turn except by creatures with haste, "
                        + "where X is the number of Bobbleheads you control as you activate this ability"));
        ability.addTarget(new TargetControlledCreaturePermanent(0, 0));
        ability.setTargetAdjuster(new AgilityBobbleheadAdjuster(xValue));
        ability.addHint(hint);
        this.addAbility(ability);
    }

    private AgilityBobblehead(final AgilityBobblehead card) {
        super(card);
    }

    @Override
    public AgilityBobblehead copy() {
        return new AgilityBobblehead(this);
    }
}

// TODO: cleanup after #12107
class AgilityBobbleheadAdjuster implements TargetAdjuster {
    private final DynamicValue dynamicValue;

    AgilityBobbleheadAdjuster(DynamicValue value) {
        this.dynamicValue = value;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int count = dynamicValue.calculate(game, ability, null);
        ability.getTargets().clear();
        if (count > 0) {
            ability.addTarget(new TargetControlledCreaturePermanent(0, count));
        }
    }
}