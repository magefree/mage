
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public final class MangarasTome extends CardImpl {

    public MangarasTome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");


        // When Mangara's Tome enters the battlefield, search your library for five cards, exile them in a face-down pile, and shuffle that pile. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MangarasTomeSearchEffect()));

        // {2}: The next time you would draw a card this turn, instead put the top card of the exiled pile into its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new MangarasTomeReplacementEffect(), new GenericManaCost(2)));
    }

    public MangarasTome(final MangarasTome card) {
        super(card);
    }

    @Override
    public MangarasTome copy() {
        return new MangarasTome(this);
    }
}

class MangarasTomeSearchEffect extends OneShotEffect {

    MangarasTomeSearchEffect() {
        super(Outcome.Neutral);
        this.staticText = "search your library for five cards, exile them in a face-down pile, and shuffle that pile. Then shuffle your library";
    }

    MangarasTomeSearchEffect(final MangarasTomeSearchEffect effect) {
        super(effect);
    }

    @Override
    public MangarasTomeSearchEffect copy() {
        return new MangarasTomeSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && permanent != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(5, new FilterCard());
            if (controller.searchLibrary(target, game)) {
                for (UUID targetId : target.getTargets()) {
                    Card card = controller.getLibrary().getCard(targetId, game);
                    if (card != null) {
                        controller.moveCardsToExile(card, source, game, false, CardUtil.getCardExileZoneId(game, source), permanent.getLogName());
                        card.setFaceDown(true, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class MangarasTomeReplacementEffect extends ReplacementEffectImpl {

    MangarasTomeReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.DrawCard);
        staticText = "The next time you would draw a card this turn, instead put the top card of the exiled pile into its owner's hand";
    }

    MangarasTomeReplacementEffect(final MangarasTomeReplacementEffect effect) {
        super(effect);
    }

    @Override
    public MangarasTomeReplacementEffect copy() {
        return new MangarasTomeReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source)).getRandom(game);
            if (card != null) {
                controller.moveCards(card, Zone.HAND, source, game);
            }
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }
}
