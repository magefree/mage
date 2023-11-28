package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.DamageCantBePreventedEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class IsengardUnleashed extends CardImpl {

    public IsengardUnleashed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}{R}");

        // Damage can't be prevented this turn. If a source you control would deal damage this turn to an opponent or a permanent an opponent controls, it deals triple that damage instead.
        this.getSpellAbility().addEffect(new DamageCantBePreventedEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new IsengardUnleashedTripleDamageEffect());
        // Flashback {4}{R}{R}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{R}{R}{R}")));

    }

    private IsengardUnleashed(final IsengardUnleashed card) {
        super(card);
    }

    @Override
    public IsengardUnleashed copy() {
        return new IsengardUnleashed(this);
    }
}
class IsengardUnleashedTripleDamageEffect extends ReplacementEffectImpl {

    public IsengardUnleashedTripleDamageEffect() {
        super(Duration.EndOfTurn, Outcome.Damage);
        staticText = "If a source you control would deal damage this turn to an opponent or a permanent an opponent controls, it deals triple that damage instead.";
    }

    private IsengardUnleashedTripleDamageEffect(final IsengardUnleashedTripleDamageEffect effect) {
        super(effect);
    }

    @Override
    public IsengardUnleashedTripleDamageEffect copy() {
        return new IsengardUnleashedTripleDamageEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!game.getControllerId(event.getSourceId()).equals(source.getControllerId())) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER) {
            return game.isOpponent(controller, event.getTargetId());
        }
        if (event.getType() == GameEvent.EventType.DAMAGE_PERMANENT) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            return permanent != null && game.isOpponent(controller, permanent.getControllerId());
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 3));
        return false;
    }
}
