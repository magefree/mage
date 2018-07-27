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
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
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
        Ability ability = new EntersBattlefieldTriggeredAbility(new CounterTargetWithReplacementEffect(Zone.EXILED));
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