package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author jimga150
 */
public final class IanMalcolmChaotician extends CardImpl {

    public IanMalcolmChaotician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a player draws their second card each turn, that player exiles the top card of their library.
        // During each player's turn, that player may cast a spell from among the cards they don't own exiled with Ian Malcolm, Chaotician, and mana of any type can be spent to cast it.
        this.addAbility(new IanMalcolmChaoticianAbility());
    }

    private IanMalcolmChaotician(final IanMalcolmChaotician card) {
        super(card);
    }

    @Override
    public IanMalcolmChaotician copy() {
        return new IanMalcolmChaotician(this);
    }
}

class IanMalcolmChaoticianAbility extends DrawNthCardTriggeredAbility {
    public IanMalcolmChaoticianAbility() {
        super(new IanMalcolmChaoticianEffect(), false, TargetController.ANY, 2);
    }

    private IanMalcolmChaoticianAbility(final IanMalcolmChaoticianAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
        return super.checkTrigger(event, game);
    }

    @Override
    public IanMalcolmChaoticianAbility copy() {
        return new IanMalcolmChaoticianAbility(this);
    }
}

// Based on CunningRhetoricEffect
class IanMalcolmChaoticianEffect extends OneShotEffect {

    IanMalcolmChaoticianEffect() {
        super(Outcome.Benefit);
        staticText = "that player exiles the top card of their library. During each player's turn, " +
                "that player may cast a spell from among the cards they don't own exiled with {this}, " +
                "and mana of any type can be spent to cast it.";
    }

    private IanMalcolmChaoticianEffect(final IanMalcolmChaoticianEffect effect) {
        super(effect);
    }

    @Override
    public IanMalcolmChaoticianEffect copy() {
        return new IanMalcolmChaoticianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        UUID targetPlayerID = getTargetPointer().getFirst(game, source);
        Player targetPlayer = game.getPlayer(targetPlayerID);
        MageObject sourceObject = source.getSourceObject(game);
        if (targetPlayer == null || sourceObject == null) {
            return false;
        }

        Card card = targetPlayer.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }

        UUID exileZoneId = CardUtil.getExileZoneId(game, sourceObject.getId(), sourceObject.getZoneChangeCounter(game));
        targetPlayer.moveCardsToExile(card, source, game, true, exileZoneId, sourceObject.getIdName());

        for (UUID playerID : game.getPlayers().keySet()){
            if (playerID.equals(targetPlayerID)) {
                continue;
            }
            CardUtil.makeCardPlayable(game, source, card, Duration.Custom, true, playerID, MyTurnCondition.instance);
        }
        return true;
    }
}
