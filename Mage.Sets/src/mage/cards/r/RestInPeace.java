
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author LevelX2
 */

//    http://www.wizards.com/magic/magazine/article.aspx?x=mtg/faq/rtr
//
//    While Rest in Peace is on the battlefield, abilities that trigger whenever a
//    creature dies won't trigger because cards and tokens never reach a player's graveyard.
//
//    If Rest in Peace is destroyed by a spell, Rest in Peace will be exiled and
//    then the spell will be put into its owner's graveyard.
//
//    If a card is discarded while Rest in Peace is on the battlefield, abilities
//    that function when a card is discarded (such as madness) still work, even
//    though that card never reaches a graveyard. In addition, spells or abilities
//    that check the characteristics of a discarded card (such as Chandra Ablaze's
//    first ability) can find that card in exile.
//

public final class RestInPeace extends CardImpl {

    public RestInPeace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");

        // When Rest in Peace enters the battlefield, exile all cards from all graveyards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExileGraveyardAllPlayersEffect()));

        // If a card or token would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RestInPeaceReplacementEffect()));
    }

    private RestInPeace(final RestInPeace card) {
        super(card);
    }

    @Override
    public RestInPeace copy() {
        return new RestInPeace(this);
    }
}

class RestInPeaceReplacementEffect extends ReplacementEffectImpl {

    public RestInPeaceReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a card or token would be put into a graveyard from anywhere, exile it instead";
    }

    private RestInPeaceReplacementEffect(final RestInPeaceReplacementEffect effect) {
        super(effect);
    }

    @Override
    public RestInPeaceReplacementEffect copy() {
        return new RestInPeaceReplacementEffect(this);
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
        return ((ZoneChangeEvent)event).getToZone() == Zone.GRAVEYARD;
    }
}
