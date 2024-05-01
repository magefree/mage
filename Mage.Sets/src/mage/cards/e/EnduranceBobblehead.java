package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EnduranceBobblehead extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.BOBBLEHEAD, "Bobbleheads you control"), null
    );
    private static final Hint hint = new ValueHint("Bobbleheads you control", xValue);

    public EnduranceBobblehead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.BOBBLEHEAD);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {3}, {T}: Up to X target creatures you control get +1/+0 and gain indestructible until end of turn, where X is the number of Bobbleheads you control as you activate this ability. Activate only as a sorcery.
        Ability ability = new SimpleActivatedAbility(
                new BoostTargetEffect(1, 0, Duration.EndOfTurn)
                        .setText("Up to X target creatures you control get +1/+0"),
                new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("and gain indestructible until end of turn, "
                        + "where X is the number of Bobbleheads you control as you activate this ability"));
        ability.addTarget(new TargetControlledCreaturePermanent(0, 0));
        ability.setTargetAdjuster(new EnduranceBobbleheadAdjuster(xValue));
        ability.addHint(hint);
        this.addAbility(ability);
    }

    private EnduranceBobblehead(final EnduranceBobblehead card) {
        super(card);
    }

    @Override
    public EnduranceBobblehead copy() {
        return new EnduranceBobblehead(this);
    }
}

// TODO: cleanup after #12107
class EnduranceBobbleheadAdjuster implements TargetAdjuster {
    private final DynamicValue dynamicValue;

    EnduranceBobbleheadAdjuster(DynamicValue value) {
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