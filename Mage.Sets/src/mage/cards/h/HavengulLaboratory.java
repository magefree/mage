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
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HavengulLaboratory extends CardImpl {

    public HavengulLaboratory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.addSuperType(SuperType.LEGENDARY);

        this.secondSideCardClazz = mage.cards.h.HavengulMystery.class;

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}, {T}: Investigate.
        Ability ability = new SimpleActivatedAbility(new InvestigateEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // At the beginning of your end step, if you sacrificed three or more Clues this turn, transform Hawkins National Laboratory.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new TransformSourceEffect(),
                TargetController.YOU, HavengulLaboratoryCondition.instance, false
        ), new HavengulLaboratoryWatcher());
    }

    private HavengulLaboratory(final HavengulLaboratory card) {
        super(card);
    }

    @Override
    public HavengulLaboratory copy() {
        return new HavengulLaboratory(this);
    }
}

enum HavengulLaboratoryCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return HavengulLaboratoryWatcher.checkPlayer(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "you sacrificed three or more Clues this turn";
    }
}

class HavengulLaboratoryWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    HavengulLaboratoryWatcher() {
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
        playerMap.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
    }

    @Override
    public void reset() {
        playerMap.clear();
        super.reset();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(HavengulLaboratoryWatcher.class)
                .playerMap
                .getOrDefault(playerId, 0) >= 3;
    }
}
