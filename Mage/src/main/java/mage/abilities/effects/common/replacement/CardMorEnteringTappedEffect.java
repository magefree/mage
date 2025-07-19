package mage.abilities.effects.common.replacement;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * Used to affect a card that will enter the battlefield (land played, or card put into play by effect).
 * The permanent it becomes enters tapped.
 * <p>
 * Short-lived replacement effect, auto-cleanup if the mor is no longer current.
 *
 * @author Susucr
 */
public class CardMorEnteringTappedEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    public CardMorEnteringTappedEffect(MageObjectReference mor) {
        super(Duration.OneUse, Outcome.Tap);
        this.staticText = "That permanent enters the battlefield tapped";
        this.mor = mor;
    }

    private CardMorEnteringTappedEffect(final CardMorEnteringTappedEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public CardMorEnteringTappedEffect copy() {
        return new CardMorEnteringTappedEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card morCard = mor.getCard(game);
        if (morCard == null) {
            // cleanup if something other than resolving happens to the spell.
            discard();
            return false;
        }
        return event.getTargetId().equals(morCard.getId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent != null) {
            permanent.setTapped(true);
        }
        return false;
    }
}
