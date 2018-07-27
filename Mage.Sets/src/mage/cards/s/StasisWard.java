package mage.cards.s;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.util.CardUtil;

/**
 *
 * @author NinthWorld
 */
public final class StasisWard extends CardImpl {

    public StasisWard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{U}");
        

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Stasis Ward enters the battlefield, counter target spell. If that spell is countered this way, exile that card until Stasis Ward leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new StasisWardCounterEffect());
        ability.addTarget(new TargetSpell());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new StasisWardReturnExiledCardAbility()));
        this.addAbility(ability);
    }

    public StasisWard(final StasisWard card) {
        super(card);
    }

    @Override
    public StasisWard copy() {
        return new StasisWard(this);
    }
}

class StasisWardCounterEffect extends OneShotEffect {

    public StasisWardCounterEffect() {
        super(Outcome.Detriment);
        staticText = "counter target spell. If that spell is countered this way, exile that card until Stasis Ward leaves the battlefield";
    }

    public StasisWardCounterEffect(final StasisWardCounterEffect effect) {
        super(effect);
    }

    @Override
    public StasisWardCounterEffect copy() {
        return new StasisWardCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(targetPointer.getFirst(game, source));
            if (game.getStack().counter(targetPointer.getFirst(game, source), source.getSourceId(), game, Zone.EXILED, false, ZoneDetail.NONE)) {
                return controller.moveCardsToExile(card, source, game, false, CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()), "Stasis Ward Exile");
            }
        }
        return false;
    }
}


// From KitesailFreebooterReturnExiledCardAbility
class StasisWardReturnExiledCardAbility extends DelayedTriggeredAbility {

    public StasisWardReturnExiledCardAbility() {
        super(new StasisWardReturnExiledCardEffect(), Duration.OneUse);
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    public StasisWardReturnExiledCardAbility(final StasisWardReturnExiledCardAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if(event.getTargetId().equals(this.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if(zEvent.getFromZone() == Zone.BATTLEFIELD) {
                return true;
            }
        }
        return false;
    }

    @Override
    public StasisWardReturnExiledCardAbility copy() {
        return new StasisWardReturnExiledCardAbility(this);
    }
}

// Modified KitesailFreebooterReturnExiledCardEffect to send card to graveyard, as the spell was already cast and countered
class StasisWardReturnExiledCardEffect extends OneShotEffect {

    public StasisWardReturnExiledCardEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return exiled card from exile";
    }

    public StasisWardReturnExiledCardEffect(final StasisWardReturnExiledCardEffect effect) {
        super(effect);
    }

    @Override
    public StasisWardReturnExiledCardEffect copy() {
        return new StasisWardReturnExiledCardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null) {
            ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()));
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (exile != null && sourcePermanent != null) {
                controller.moveCards(exile, Zone.GRAVEYARD, source, game);
                return true;
            }
        }
        return false;
    }
}