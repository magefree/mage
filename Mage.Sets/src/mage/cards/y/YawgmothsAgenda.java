
package mage.cards.y;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.CantCastMoreThanOneSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class YawgmothsAgenda extends CardImpl {

    public YawgmothsAgenda(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}{B}");

        // You can't cast more than one spell each turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantCastMoreThanOneSpellEffect(TargetController.YOU)));
        // You may play cards from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new YawgmothsAgendaCanPlayCardsFromGraveyardEffect()));
        // If a card would be put into your graveyard from anywhere, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new YawgmothsAgendaReplacementEffect()));
    }

    private YawgmothsAgenda(final YawgmothsAgenda card) {
        super(card);
    }

    @Override
    public YawgmothsAgenda copy() {
        return new YawgmothsAgenda(this);
    }
}

class YawgmothsAgendaCanPlayCardsFromGraveyardEffect extends ContinuousEffectImpl {

    public YawgmothsAgendaCanPlayCardsFromGraveyardEffect() {
        this(Duration.WhileOnBattlefield);
    }

    public YawgmothsAgendaCanPlayCardsFromGraveyardEffect(Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "You may play cards from your graveyard";
    }

    private YawgmothsAgendaCanPlayCardsFromGraveyardEffect(final YawgmothsAgendaCanPlayCardsFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public YawgmothsAgendaCanPlayCardsFromGraveyardEffect copy() {
        return new YawgmothsAgendaCanPlayCardsFromGraveyardEffect(this);
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

class YawgmothsAgendaReplacementEffect extends ReplacementEffectImpl {

    public YawgmothsAgendaReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "If a card would be put into your graveyard from anywhere, exile it instead";
    }

    private YawgmothsAgendaReplacementEffect(final YawgmothsAgendaReplacementEffect effect) {
        super(effect);
    }

    @Override
    public YawgmothsAgendaReplacementEffect copy() {
        return new YawgmothsAgendaReplacementEffect(this);
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
