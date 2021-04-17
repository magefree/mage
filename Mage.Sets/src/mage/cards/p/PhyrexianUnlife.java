package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.DontLoseByZeroOrLessLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class PhyrexianUnlife extends CardImpl {

    public PhyrexianUnlife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // You don't lose the game for having 0 or less life.
        this.addAbility(new SimpleStaticAbility(new DontLoseByZeroOrLessLifeEffect(Duration.WhileOnBattlefield)));

        // As long as you have 0 or less life, all damage is dealt to you as though its source had infect.
        this.addAbility(new SimpleStaticAbility(new PhyrexianUnlifeEffect()));
    }

    private PhyrexianUnlife(final PhyrexianUnlife card) {
        super(card);
    }

    @Override
    public PhyrexianUnlife copy() {
        return new PhyrexianUnlife(this);
    }
}

class PhyrexianUnlifeEffect extends ReplacementEffectImpl {

    private int lastCheckedDamageStepNum = 0;
    private boolean startedWithLifeAboveZero = false;

    PhyrexianUnlifeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As long as you have 0 or less life, all damage is dealt to you as though its source had infect";
    }

    private PhyrexianUnlifeEffect(final PhyrexianUnlifeEffect effect) {
        super(effect);
        this.lastCheckedDamageStepNum = effect.lastCheckedDamageStepNum;
        this.startedWithLifeAboveZero = effect.startedWithLifeAboveZero;
    }

    @Override
    public PhyrexianUnlifeEffect copy() {
        return new PhyrexianUnlifeEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((DamageEvent) event).setAsThoughInfect(true);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!event.getPlayerId().equals(source.getControllerId())) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        // The decision if the player has more than 0 life has to be checked only at start of a combat damage step
        // and all damage in the same step has to be handled the same beause by the rules it's all done at once
        if (((DamageEvent) event).isCombatDamage()) {
            if (lastCheckedDamageStepNum != game.getState().getStepNum()) {
                lastCheckedDamageStepNum = game.getState().getStepNum();
                startedWithLifeAboveZero = player.getLife() > 0;
            }
            if (startedWithLifeAboveZero) {
                return false;
            }
        }
        if (player.getLife() < 1) {
            return true;
        }
        return false;
    }
}
