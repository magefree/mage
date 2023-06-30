package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BattlefieldButcher extends CardImpl {

    public BattlefieldButcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {5}, {T}: Each opponent loses 2 life. This ability costs {1} less to activate for each creature card in your graveyard.
        Ability ability = new SimpleActivatedAbility(new LoseLifeOpponentsEffect(2), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new InfoEffect("this ability costs {1} less to activate for each creature card in your graveyard"));
        this.addAbility(ability.setCostAdjuster(BattlefieldButcherAdjuster.instance).addHint(BattlefieldButcherAdjuster.getHint()));
    }

    private BattlefieldButcher(final BattlefieldButcher card) {
        super(card);
    }

    @Override
    public BattlefieldButcher copy() {
        return new BattlefieldButcher(this);
    }
}

enum BattlefieldButcherAdjuster implements CostAdjuster {
    instance;
    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);
    private static final Hint hint = new ValueHint("Creature cards in your graveyard", xValue);

    @Override
    public void adjustCosts(Ability ability, Game game) {
        CardUtil.reduceCost(ability, xValue.calculate(game, ability, null));
    }

    public static Hint getHint() {
        return hint;
    }
}