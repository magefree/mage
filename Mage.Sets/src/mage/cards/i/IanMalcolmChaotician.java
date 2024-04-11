package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

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
        this.addAbility(new DrawNthCardTriggeredAbility(
                new IanMalcolmChaoticianEffect(), false, TargetController.ANY, 2));
    }

    private IanMalcolmChaotician(final IanMalcolmChaotician card) {
        super(card);
    }

    @Override
    public IanMalcolmChaotician copy() {
        return new IanMalcolmChaotician(this);
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
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (opponent == null || sourceObject == null) {
            return false;
        }
        Card card = opponent.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }

        UUID exileZoneId = CardUtil.getExileZoneId(game, sourceObject.getId(), sourceObject.getZoneChangeCounter(game));
        opponent.moveCardsToExile(card, source, game, true, exileZoneId, sourceObject.getIdName());
        CardUtil.makeCardPlayable(game, source, card, Duration.Custom, true);
        return true;
    }
}
