package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author brikr
 */
public final class AweStrike extends CardImpl {

    public AweStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // The next time target creature would deal damage this turn, prevent that damage. You gain life equal to the damage prevented this way.
        this.getSpellAbility().addEffect(new AweStrikeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AweStrike(final AweStrike card) {
        super(card);
    }

    @Override
    public AweStrike copy() {
        return new AweStrike(this);
    }

}

class AweStrikeEffect extends PreventionEffectImpl {

    public AweStrikeEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        this.staticText = "The next time target creature would deal damage this turn, prevent that damage. You gain life equal to the damage prevented this way";
    }

    private AweStrikeEffect(final AweStrikeEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife(preventionData.getPreventedDamage(), game, source);
        }
        this.used = true;
        this.discard();
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            return targetCreature != null && targetCreature.getId().equals(event.getSourceId());
        }
        return false;
    }

    @Override
    public AweStrikeEffect copy() {
        return new AweStrikeEffect(this);
    }
}
