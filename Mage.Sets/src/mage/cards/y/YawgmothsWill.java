
package mage.cards.y;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class YawgmothsWill extends CardImpl {

    public YawgmothsWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Until end of turn, you may play cards from your graveyard.
        this.getSpellAbility().addEffect(new CanPlayCardsFromGraveyardEffect());

        // If a card would be put into your graveyard from anywhere this turn, exile that card instead.
        this.getSpellAbility().addEffect(new YawgmothsWillReplacementEffect());
    }

    private YawgmothsWill(final YawgmothsWill card) {
        super(card);
    }

    @Override
    public YawgmothsWill copy() {
        return new YawgmothsWill(this);
    }
}

class CanPlayCardsFromGraveyardEffect extends ContinuousEffectImpl {

    public CanPlayCardsFromGraveyardEffect() {
        this(Duration.EndOfTurn);
    }

    public CanPlayCardsFromGraveyardEffect(Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "Until end of turn, you may play cards from your graveyard";
    }

    private CanPlayCardsFromGraveyardEffect(final CanPlayCardsFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public CanPlayCardsFromGraveyardEffect copy() {
        return new CanPlayCardsFromGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.setPlayCardsFromGraveyard(true);
            return true;
        }
        return false;
    }

}

class YawgmothsWillReplacementEffect extends ReplacementEffectImpl {

    public YawgmothsWillReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        this.staticText = "If a card would be put into your graveyard from anywhere this turn, exile that card instead";
    }

    private YawgmothsWillReplacementEffect(final YawgmothsWillReplacementEffect effect) {
        super(effect);
    }

    @Override
    public YawgmothsWillReplacementEffect copy() {
        return new YawgmothsWillReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.isOwnedBy(source.getControllerId())) {
                Permanent permanent = ((ZoneChangeEvent) event).getTarget();
                if (!(permanent instanceof PermanentToken)) {
                    return true;
                }
            }
        }
        return false;
    }

}
