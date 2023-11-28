package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class HeatWave extends CardImpl {

    public HeatWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Cumulative upkeep {R}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{R}")));

        // Blue creatures can't block creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HeatWaveEffect1()));

        // Nonblue creatures can't block creatures you control unless their controller pays 1 life for each blocking creature they control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HeatWaveEffect2()));

    }

    private HeatWave(final HeatWave card) {
        super(card);
    }

    @Override
    public HeatWave copy() {
        return new HeatWave(this);
    }
}

class HeatWaveEffect1 extends RestrictionEffect {

    HeatWaveEffect1() {
        super(Duration.WhileOnBattlefield);
        staticText = "Blue creatures can't block creatures you control";
    }

    private HeatWaveEffect1(final HeatWaveEffect1 effect) {
        super(effect);
    }

    @Override
    public ContinuousEffect copy() {
        return new HeatWaveEffect1(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return attacker == null || !attacker.isControlledBy(source.getControllerId()) || !blocker.getColor(game).isBlue();
    }
}

class HeatWaveEffect2 extends ReplacementEffectImpl {

    HeatWaveEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Nonblue creatures can't block creatures you control unless their controller pays 1 life for each blocking creature they control";
    }

    private HeatWaveEffect2(final HeatWaveEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player == null) {
            return false;
        }

        Permanent blocker = game.getPermanent(event.getSourceId());
        if (blocker == null || blocker.getColor(game).isBlue()) {
            return false;
        }

        Permanent attacker = game.getPermanent(event.getTargetId());
        if (attacker == null || !attacker.isControlledBy(source.getControllerId())) {
            return false;
        }

        PayLifeCost cost = new PayLifeCost(1);
        if (cost.canPay(source, source, player.getId(), game)
                && player.chooseUse(Outcome.Benefit, "Pay 1 life to declare blocker?", source, game)) {
            return !cost.pay(source, game, source, player.getId(), true, cost);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARE_BLOCKER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public ReplacementEffect copy() {
        return new HeatWaveEffect2(this);
    }
}
