package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.SourceControllerCountersCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MariposaMilitaryBase extends CardImpl {

    public MariposaMilitaryBase(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // You may have Mariposa Military Base enter the battlefield tapped. If you do, you get two rad counters.
        Ability ability = new EntersBattlefieldAbility(
                new TapSourceEffect(true).setText("tapped."),
                true
        );
        ability.addEffect(new AddCountersPlayersEffect(CounterType.RAD.createInstance(2), TargetController.YOU)
                .setText("If you do, you get two rad counters."));
        this.addAbility(ability);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {5}, {T}: Draw a card. This ability costs {1} less to activate for each rad counter you have.
        ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(5));
        ability.addEffect(new InfoEffect("This ability costs {1} less to activate for each rad counter you have"));
        ability.addCost(new TapSourceCost());
        ability.setCostAdjuster(MariposaMilitaryBaseAdjuster.instance);
        this.addAbility(ability);
    }

    private MariposaMilitaryBase(final MariposaMilitaryBase card) {
        super(card);
    }

    @Override
    public MariposaMilitaryBase copy() {
        return new MariposaMilitaryBase(this);
    }
}

enum MariposaMilitaryBaseAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        CardUtil.reduceCost(ability, SourceControllerCountersCount.RAD.calculate(game, ability, null));
    }
}
