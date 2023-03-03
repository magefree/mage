package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.Ability;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;
import java.util.*;
/**
 *
 * @author @stwalsh4118
 */
public final class Bloodthirster extends CardImpl {

    public Bloodthirster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");
        
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Bloodthirster deals combat damage to a player, untap it. After this combat phase, there is an additional combat phase.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new UntapSourceEffect().setText("untap it"), false, true);
        ability.addEffect(new AdditionalCombatPhaseEffect("After this combat phase, there is an additional combat phase"));
        this.addAbility(ability);

        // Bloodthirster can't attack a player it has already attacked this turn.
        this.addAbility(new SimpleStaticAbility(new BloodthirsterEffect()), new BloodthirsterWatcher());

    }

    private Bloodthirster(final Bloodthirster card) {
        super(card);
    }

    @Override
    public Bloodthirster copy() {
        return new Bloodthirster(this);
    }
}

class BloodthirsterEffect extends RestrictionEffect {

    BloodthirsterEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack a player it has already attacked this turn";
    }

    private BloodthirsterEffect(final BloodthirsterEffect effect) {
        super(effect);
    }

    @Override
    public BloodthirsterEffect copy() {
        return new BloodthirsterEffect(this);
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        BloodthirsterWatcher watcher = game.getState().getWatcher(BloodthirsterWatcher.class);
        return watcher != null && watcher.checkAttacker(attacker, defenderId);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }
}

class BloodthirsterWatcher extends Watcher {

    private final Map<UUID, Set<UUID>> attackMap = new HashMap<>();

    BloodthirsterWatcher() {
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
