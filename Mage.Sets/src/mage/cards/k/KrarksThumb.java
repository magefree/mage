
package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.FlipCoinEvent;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KrarksThumb extends CardImpl {

    public KrarksThumb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.supertype.add(SuperType.LEGENDARY);

        // If you would flip a coin, instead flip two coins and ignore one.
        this.addAbility(new SimpleStaticAbility(new KrarksThumbEffect()));
    }

    private KrarksThumb(final KrarksThumb card) {
        super(card);
    }

    @Override
    public KrarksThumb copy() {
        return new KrarksThumb(this);
    }
}

class KrarksThumbEffect extends ReplacementEffectImpl {

    KrarksThumbEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would flip a coin, instead flip two coins and ignore one.";
    }

    private KrarksThumbEffect(final KrarksThumbEffect effect) {
        super(effect);
    }

    @Override
    public KrarksThumbEffect copy() {
        return new KrarksThumbEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        FlipCoinEvent flipCoinEvent = (FlipCoinEvent) event;
        // If an effect tells you to flip more than one coin at once, this replaces each individual coin flip.
        // For example, if an effect tells you to flip two coins, you’ll first flip two coins and ignore one, then
        // flip two more coins and ignore one of those. You don’t flip four coins and ignore two.
        // (2013-04-15)
        flipCoinEvent.setFlipCount(1 + flipCoinEvent.getFlipCount());
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.FLIP_COIN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }
}
