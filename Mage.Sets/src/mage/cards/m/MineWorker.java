package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class MineWorker extends CardImpl {

    public MineWorker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.ASSEMBLY_WORKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: You gain 1 life. If you control creatures named Power Plant Worker and Tower Worker, you gain 3 life instead.
        this.addAbility(new SimpleActivatedAbility(new MineWorkerEffect(), new TapSourceCost()));
    }

    private MineWorker(final MineWorker card) {
        super(card);
    }

    @Override
    public MineWorker copy() {
        return new MineWorker(this);
    }
}

class MineWorkerEffect extends OneShotEffect {

    public MineWorkerEffect() {
        super(Outcome.GainLife);
        this.staticText = "You gain 1 life. If you control creatures named Power Plant Worker and Tower Worker, you gain 3 life instead.";
    }

    private MineWorkerEffect(final MineWorkerEffect effect) {
        super(effect);
    }

    @Override
    public MineWorkerEffect copy() {
        return new MineWorkerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        String powerPlantName = "Power Plant Worker";
        String towerName = "Tower Worker";
        boolean powerPlant = false;
        boolean tower = false;
        int life = 1;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), game)) {
            String name = permanent.getName();
            if (!powerPlant && powerPlantName.equals(name)) {
                powerPlant = true;
            } else if (!tower && towerName.equals(name)) {
                tower = true;
            }
            if (powerPlant && tower) {
                life = 3;
                break;
            }
        }
        controller.gainLife(life, game, source);
        return true;
    }
}
