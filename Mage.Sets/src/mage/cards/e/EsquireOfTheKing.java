package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EsquireOfTheKing extends CardImpl {

    private static final Hint hint = new ConditionHint(
            new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY),
            "You control a legendary creature"
    );

    public EsquireOfTheKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {4}{W}, {T}: Creatures you control get +1/+1 until end of turn. This ability costs {2} less to activate if you control a legendary creature.
        Ability ability = new SimpleActivatedAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{4}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.setCostAdjuster(EsquireOfTheKingAdjuster.instance);
        ability.addEffect(new InfoEffect("This ability costs {2} less to activate if you control a legendary creature."));
        this.addAbility(ability.addHint(hint));
    }

    private EsquireOfTheKing(final EsquireOfTheKing card) {
        super(card);
    }

    @Override
    public EsquireOfTheKing copy() {
        return new EsquireOfTheKing(this);
    }
}

enum EsquireOfTheKingAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (game.getBattlefield().contains(StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY, ability, game, 1)) {
            CardUtil.reduceCost(ability, 2);
        }
    }
}
