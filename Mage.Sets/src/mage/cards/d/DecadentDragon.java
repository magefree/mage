package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.asthought.MayLookAtTargetCardEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class DecadentDragon extends AdventureCard {

    public DecadentDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{2}{R}{R}", "Expensive Taste", "{2}{B}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Decadent Dragon attacks, create a Treasure token.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // Expensive Taste
        // Exile the top two cards of target opponent's library face down. You may look at and play those cards for as long as they remain exiled.
        this.getSpellCard().getSpellAbility().addEffect(new ExpensiveTasteEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetOpponent());

        this.finalizeAdventure();
    }

    private DecadentDragon(final DecadentDragon card) {
        super(card);
    }

    @Override
    public DecadentDragon copy() {
        return new DecadentDragon(this);
    }
}

class ExpensiveTasteEffect extends OneShotEffect {

    private static final String VALUE_PREFIX = "ExileZones";

    ExpensiveTasteEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top two cards of target opponent's library face down. You may look at and play those cards for as long as they remain exiled.";
    }

    private ExpensiveTasteEffect(final ExpensiveTasteEffect effect) {
        super(effect);
    }

    @Override
    public ExpensiveTasteEffect copy() {
        return new ExpensiveTasteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = source.getSourceObject(game);
        if (opponent == null || controller == null || sourceObject == null) {
            return false;
        }

        Cards topCards = new CardsImpl();
        topCards.addAllCards(opponent.getLibrary().getTopCards(game, 2));

        for (Card card : topCards.getCards(game)) {
            UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            card.setFaceDown(true, game);
            if (controller.moveCardsToExile(card, source, game, false, exileZoneId, sourceObject.getIdName())) {
                card.setFaceDown(true, game);
                Set<UUID> exileZones = (Set<UUID>) game.getState().getValue(VALUE_PREFIX + source.getSourceId().toString());
                if (exileZones == null) {
                    exileZones = new HashSet<>();
                    game.getState().setValue(VALUE_PREFIX + source.getSourceId().toString(), exileZones);
                }
                exileZones.add(exileZoneId);
                // allow to cast the card
                CardUtil.makeCardPlayable(game, source, card, Duration.EndOfGame, false, controller.getId(), null);
                // For as long as that card remains exiled, you may look at it
                ContinuousEffect effect = new MayLookAtTargetCardEffect(controller.getId());
                effect.setTargetPointer(new FixedTarget(card.getId(), game));
                game.addEffect(effect, source);
            }
        }
        return true;
    }

}
