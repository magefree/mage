package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class DeepwoodDenizen extends CardImpl {

    public DeepwoodDenizen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {5}{G}, {T}: Draw a card. This ability costs {1} less to activate for each +1/+1 counter on creatures you control.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{5}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new InfoEffect("This ability costs {1} less to activate for each +1/+1 counter on creatures you control"));
        ability.setCostAdjuster(DeepwoodDenizenAdjuster.instance);
        ability.addHint(new ValueHint("+1/+1 counters on creatures you control", DeepwoodDenizenValue.instance));
        this.addAbility(ability);
    }

    private DeepwoodDenizen(final DeepwoodDenizen card) {
        super(card);
    }

    @Override
    public DeepwoodDenizen copy() {
        return new DeepwoodDenizen(this);
    }
}

enum DeepwoodDenizenAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        CardUtil.reduceCost(ability, DeepwoodDenizenValue.instance.calculate(game, ability, null));
    }
}

enum DeepwoodDenizenValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int counters = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(sourceAbility.getControllerId())) {
            if (permanent.isCreature(game)) {
                counters += permanent.getCounters(game).getCount(CounterType.P1P1);
            }
        }
        return counters;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "+1/+1 counter on creatures you control";
    }

    @Override
    public String toString() {
        return "X";
    }
}
