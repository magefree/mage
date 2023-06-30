package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.mana.DynamicManaAbility;
import mage.constants.SubType;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author weirddan455
 */
public final class TowerWorker extends CardImpl {

    public TowerWorker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.ASSEMBLY_WORKER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {T}: Add {C}. If you control creatures named Mine Worker and Power Plant Worker, add {C}{C}{C} instead.
        this.addAbility(new DynamicManaAbility(
                Mana.ColorlessMana(1), TowerWorkerValue.instance,
                "Add {C}. If you control creatures named Mine Worker and Power Plant Worker, add {C}{C}{C} instead"
        ));
    }

    private TowerWorker(final TowerWorker card) {
        super(card);
    }

    @Override
    public TowerWorker copy() {
        return new TowerWorker(this);
    }
}

enum TowerWorkerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        String mineName = "Mine Worker";
        String powerPlantName = "Power Plant Worker";
        boolean mine = false;
        boolean powerPlant = false;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, sourceAbility.getControllerId(), game)) {
            String name = permanent.getName();
            if (!mine && mineName.equals(name)) {
                mine = true;
            } else if (!powerPlant && powerPlantName.equals(name)) {
                powerPlant = true;
            }
            if (mine && powerPlant) {
                return 3;
            }
        }
        return 1;
    }

    @Override
    public TowerWorkerValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
