package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrimaWormtongue extends CardImpl {

    public GrimaWormtongue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Your opponents can't gain life.
        this.addAbility(new SimpleStaticAbility(new CantGainLifeAllEffect(
                Duration.WhileOnBattlefield, TargetController.OPPONENT
        )));

        // {T}, Sacrifice another creature: Target player loses 1 life. If the sacrificed creature was legendary, amass Orcs 2.
        Ability ability = new SimpleActivatedAbility(new LoseLifeTargetEffect(1), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        ability.addEffect(new ConditionalOneShotEffect(
                new AmassEffect(2, SubType.ORC), GrimaWormtongueCondition.instance,
                "If the sacrificed creature was legendary, amass Orcs 2"
        ));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private GrimaWormtongue(final GrimaWormtongue card) {
        super(card);
    }

    @Override
    public GrimaWormtongue copy() {
        return new GrimaWormtongue(this);
    }
}

enum GrimaWormtongueCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil
                .castStream(source.getCosts().stream(), SacrificeTargetCost.class)
                .map(SacrificeTargetCost::getPermanents)
                .flatMap(Collection::stream)
                .anyMatch(permanent -> permanent.isLegendary(game));
    }
}
