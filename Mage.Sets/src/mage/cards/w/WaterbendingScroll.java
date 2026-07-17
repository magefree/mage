package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WaterbendingScroll extends CardImpl {

    public WaterbendingScroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        // {6}, {T}: Draw a card. This ability costs {1} less to activate for each Island you control.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(6));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new InfoEffect("This ability costs {1} less to activate for each Island you control"));
        this.addAbility(ability.setCostAdjuster(WaterbendingScrollAdjuster.instance).addHint(WaterbendingScrollAdjuster.getHint()));
    }

    private WaterbendingScroll(final WaterbendingScroll card) {
        super(card);
    }

    @Override
    public WaterbendingScroll copy() {
        return new WaterbendingScroll(this);
    }
}

enum WaterbendingScrollAdjuster implements CostAdjuster {
    instance;
    private static final DynamicValue cardsCount = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.ISLAND));
    private static final Hint hint = new ValueHint("Islands you control", cardsCount);

    static Hint getHint() {
        return hint;
    }

    @Override
    public void reduceCost(Ability ability, Game game) {
        int count = cardsCount.calculate(game, ability, null);
        CardUtil.reduceCost(ability, count);
    }
}
