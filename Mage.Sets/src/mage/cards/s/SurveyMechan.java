package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.DifferentlyNamedPermanentCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.SecondTargetPointer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SurveyMechan extends CardImpl {

    public SurveyMechan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // {10}, Sacrifice this creature: It deals 3 damage to any target. Target player draws three cards and gains 3 life. This ability costs {X} less to activate, where X is the number of differently named lands you control.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(3, "it"), new GenericManaCost(10)
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget().withChooseHint("to deal damage to"));
        ability.addEffect(new DrawCardTargetEffect(3).setTargetPointer(new SecondTargetPointer()));
        ability.addEffect(new GainLifeTargetEffect(3).setTargetPointer(new SecondTargetPointer()).setText("and gains 3 life"));
        ability.addTarget(new TargetPlayer().withChooseHint("to draw three cards and gain 3 life"));
        ability.addEffect(new InfoEffect("This ability costs {X} less to activate, " +
                "where X is the number of differently named lands you control"));
        this.addAbility(ability.setCostAdjuster(SurveyMechanAdjuster.instance).addHint(SurveyMechanAdjuster.getHint()));
    }

    private SurveyMechan(final SurveyMechan card) {
        super(card);
    }

    @Override
    public SurveyMechan copy() {
        return new SurveyMechan(this);
    }
}

enum SurveyMechanAdjuster implements CostAdjuster {
    instance;
    private static final DifferentlyNamedPermanentCount xValue
            = new DifferentlyNamedPermanentCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS);

    static Hint getHint() {
        return xValue.getHint();
    }

    @Override
    public void reduceCost(Ability ability, Game game) {
        int amount = xValue.calculate(game, ability, null);
        if (amount > 0) {
            CardUtil.reduceCost(ability, amount);
        }
    }
}
