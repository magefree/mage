package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.InklingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Inkshield extends CardImpl {

    public Inkshield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}{B}");

        // Prevent all combat damage that would be dealt to you this turn. For each 1 damage prevented this way, create a 2/1 white and black Inkling creature token with flying.
        this.getSpellAbility().addEffect(new InkshieldEffect());
    }

    private Inkshield(final Inkshield card) {
        super(card);
    }

    @Override
    public Inkshield copy() {
        return new Inkshield(this);
    }
}

class InkshieldEffect extends PreventionEffectImpl {

    InkshieldEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, true, false);
        staticText = "prevent all combat damage that would be dealt to you this turn. " +
                "For each 1 damage prevented this way, create a 2/1 white and black Inkling creature token with flying";
    }

    private InkshieldEffect(final InkshieldEffect effect) {
        super(effect);
    }

    @Override
    public InkshieldEffect copy() {
        return new InkshieldEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game) && source.isControlledBy(event.getTargetId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionEffectData = preventDamageAction(event, source, game);
        if (preventionEffectData.getPreventedDamage() > 0) {
            new CreateTokenEffect(new InklingToken(), preventionEffectData.getPreventedDamage()).apply(game, source);
        }
        return true;
    }
}
