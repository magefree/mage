package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class PortRazer extends CardImpl {

    public PortRazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Port Razer deals combat damage to a player, untap each creature you control. After this combat phase, there is an additional combat phase.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new UntapAllControllerEffect(
                        StaticFilters.FILTER_CONTROLLED_CREATURES,
                        "untap all creatures you control"
                ), false
        );
        ability.addEffect(new AdditionalCombatPhaseEffect()
                .setText("After this combat phase, there is an additional combat phase."));
        this.addAbility(ability);

        // Port Razer can't attack a player it has already attacked this turn.
        this.addAbility(new SimpleStaticAbility(new PortRazerEffect()), new PortRazerWatcher());
    }

    private PortRazer(final PortRazer card) {
        super(card);
    }

    @Override
    public PortRazer copy() {
        return new PortRazer(this);
    }
}

class PortRazerEffect extends RestrictionEffect {

    PortRazerEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack a player it has already attacked this turn";
    }

    private PortRazerEffect(final PortRazerEffect effect) {
        super(effect);
    }

    @Override
    public PortRazerEffect copy() {
        return new PortRazerEffect(this);
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        PortRazerWatcher watcher = game.getState().getWatcher(PortRazerWatcher.class);
        return watcher != null && watcher.checkAttacker(attacker, defenderId);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }
}

class PortRazerWatcher extends Watcher {

    private final Map<UUID, Set<UUID>> attackMap = new HashMap<>();

    PortRazerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ATTACKER_DECLARED
                || game.getPlayer(event.getTargetId()) == null) {
            return;
        }
        attackMap.computeIfAbsent(event.getSourceId(), x -> new HashSet<>()).add(event.getTargetId());
    }

    @Override
    public void reset() {
        attackMap.clear();
        super.reset();
    }

    boolean checkAttacker(Permanent permanent, UUID defenderId) {
        return !attackMap.computeIfAbsent(permanent.getId(), x -> new HashSet<>()).contains(defenderId);
    }
}
