package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MirrorOfGaladriel extends CardImpl {

    public MirrorOfGaladriel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);

        // {5}, {T}: Scry 1, then draw a card. This ability costs {1} less to activate for each legendary creature you control.
        Ability ability = new SimpleActivatedAbility(
                new ScryEffect(1, false), new GenericManaCost(5)
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        ability.addEffect(new InfoEffect(
                "This ability costs {1} less to activate for each legendary creature you control."
        ));
        ability.setCostAdjuster(MirrorOfGaladrielAdjuster.instance);
        this.addAbility(ability.addHint(MirrorOfGaladrielAdjuster.getHint()));
    }

    private MirrorOfGaladriel(final MirrorOfGaladriel card) {
        super(card);
    }

    @Override
    public MirrorOfGaladriel copy() {
        return new MirrorOfGaladriel(this);
    }
}

enum MirrorOfGaladrielAdjuster implements CostAdjuster {
    instance;
    private static final Hint hint = new ValueHint(
            "Legendary creatures you control",
            new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY)
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int value = game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY,
                ability.getControllerId(), ability, game
        );
        if (value > 0) {
            CardUtil.reduceCost(ability, value);
        }
    }
}
