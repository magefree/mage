package mage.cards.i;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.CitizenGreenWhiteToken;
import mage.game.permanent.token.TreasureToken;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class IndulgeExcess extends SplitCard {

    public IndulgeExcess(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new CardType[]{CardType.SORCERY},
                "{2}{R}", "{1}{R}", SpellAbilityType.SPLIT_AFTERMATH
        );

        // Indulge
        // Whenever a creature you control attacks this turn, create a 1/1 green and white Citizen creature token that's tapped and attacking.
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new CreateDelayedTriggeredAbilityEffect(new IndulgeTriggeredAbility())
        );

        // Excess
        // Aftermath
        // Create a Treasure token for each creature you controlled that dealt combat damage to a player this turn.
        this.getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        this.getRightHalfCard().getSpellAbility().addEffect(
                new CreateTokenEffect(new TreasureToken(), ExcessValue.instance)
        );
        this.getRightHalfCard().getSpellAbility().addWatcher(new ExcessWatcher());
    }

    private IndulgeExcess(final IndulgeExcess card) {
        super(card);
    }

    @Override
    public IndulgeExcess copy() {
        return new IndulgeExcess(this);
    }
}

class IndulgeTriggeredAbility extends DelayedTriggeredAbility {

    IndulgeTriggeredAbility() {
        super(new CreateTokenEffect(
                new CitizenGreenWhiteToken(), 1, true, true
        ), Duration.EndOfTurn, false, false);
        setTriggerPhrase("Whenever a creature you control attacks this turn, ");
    }

    private IndulgeTriggeredAbility(final IndulgeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public IndulgeTriggeredAbility copy() {
        return new IndulgeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(game.getControllerId(event.getSourceId()));
    }
}

enum ExcessValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return ExcessWatcher.getCount(sourceAbility.getControllerId(), game);
    }

    @Override
    public ExcessValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "creature you controlled that dealt combat damage to a player this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class ExcessWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> map = new HashMap<>();

    ExcessWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_PLAYER || !((DamagedEvent) event).isCombatDamage()) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent == null) {
            return;
        }
        map.computeIfAbsent(
                permanent.getControllerId(), x -> new HashSet<>()
        ).add(new MageObjectReference(permanent, game));
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static int getCount(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(ExcessWatcher.class)
                .map
                .getOrDefault(playerId, Collections.emptySet())
                .size();
    }
}
