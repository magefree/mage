package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class OKagachiVengefulKami extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanent that player controls");

    public OKagachiVengefulKami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever O-Kagachi, Vengeful Kami deals combat damage to a player, if that player attacked you during their last turn, exile target nonland permanent that player controls
        TriggeredAbility ability = new DealsCombatDamageToAPlayerTriggeredAbility(new ExileTargetEffect(), false, true);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        ability.withInterveningIf(KagachiVengefulKamiCondition.instance);

        this.addAbility(ability, new OKagachiVengefulKamiWatcher());
    }

    private OKagachiVengefulKami(final OKagachiVengefulKami card) {
        super(card);
    }

    @Override
    public OKagachiVengefulKami copy() {
        return new OKagachiVengefulKami(this);
    }
}

enum KagachiVengefulKamiCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        OKagachiVengefulKamiWatcher watcher = game.getState().getWatcher(OKagachiVengefulKamiWatcher.class);
        if (watcher == null) {
            return false;
        }
        return CardUtil.getEffectValueFromAbility(source, "damagedPlayer", UUID.class)
                .filter(uuid -> watcher.checkPlayer(source.getControllerId(), uuid))
                .isPresent();
    }

    @Override
    public String toString() {
        return "if that player attacked you during their last turn";
    }

}

class OKagachiVengefulKamiWatcher extends Watcher {

    private final Map<UUID, Set<UUID>> playerMap = new HashMap<>();

    OKagachiVengefulKamiWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case BEGINNING_PHASE_PRE:
                playerMap.remove(game.getActivePlayerId());
                return;
            case ATTACKER_DECLARED:
                UUID attacker = event.getPlayerId();
                Set<UUID> defenders = playerMap.get(attacker);
                if (defenders == null) {
                    defenders = new HashSet<>();
                }
                defenders.add(event.getTargetId());
                playerMap.put(attacker, defenders);
        }
    }

    boolean checkPlayer(UUID attackerId, UUID defenderId) {
        if (attackerId != null && defenderId != null) {
            Set<UUID> defendersLastTurn = playerMap.get(defenderId);
            if (defendersLastTurn != null) {
                return defendersLastTurn.contains(attackerId);
            }
        }
        return false;
    }
}
