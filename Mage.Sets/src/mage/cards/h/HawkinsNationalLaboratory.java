package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HawkinsNationalLaboratory extends CardImpl {

    public HawkinsNationalLaboratory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.transformable = true;
        this.secondSideCardClazz = mage.cards.t.TheUpsideDown.class;

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}, {T}: Investigate.
        Ability ability = new SimpleActivatedAbility(new InvestigateEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // At the beginning of your end step, if you sacrificed three or more Clues this turn, transform Hawkins National Laboratory.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new TransformSourceEffect(true),
                TargetController.YOU, HawkinsNationalLaboratoryCondition.instance, false
        ), new HawkinsNationalLaboratoryWatcher());
    }

    private HawkinsNationalLaboratory(final HawkinsNationalLaboratory card) {
        super(card);
    }

    @Override
    public HawkinsNationalLaboratory copy() {
        return new HawkinsNationalLaboratory(this);
    }
}

enum HawkinsNationalLaboratoryCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return HawkinsNationalLaboratoryWatcher.checkPlayer(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "you sacrificed three or more Clues this turn";
    }
}

class HawkinsNationalLaboratoryWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    HawkinsNationalLaboratoryWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SACRIFICED_PERMANENT) {
            return;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (permanent == null || !permanent.hasSubtype(SubType.CLUE, game)) {
            return;
        }
        playerMap.compute(event.getPlayerId(), (u, i) -> i == null ? 1 : Integer.sum(i, 1));
    }

    @Override
    public void reset() {
        playerMap.clear();
        super.reset();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(HawkinsNationalLaboratoryWatcher.class)
                .playerMap
                .getOrDefault(playerId, 0) >= 3;
    }
}
