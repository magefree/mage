package mage.cards.t;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.util.CardUtil;
import mage.util.RandomUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author weirddan455
 */
public final class TibaltsTrickery extends CardImpl {

    public TibaltsTrickery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Counter target spell. Choose 1, 2, or 3 at random. Its controller mills that many cards,
        // then exiles cards from the top of their library until they exile a nonland card
        // with a different name than that spell. They may cast that card without paying its mana cost.
        // Then they put the exiled cards on the bottom of their library in a random order.
        this.getSpellAbility().addEffect(new TibaltsTrickeryEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private TibaltsTrickery(final TibaltsTrickery card) {
        super(card);
    }

    @Override
    public TibaltsTrickery copy() {
        return new TibaltsTrickery(this);
    }
}

class TibaltsTrickeryEffect extends OneShotEffect {

    TibaltsTrickeryEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target spell. Choose 1, 2, or 3 at random. Its controller mills that many cards, "
                + "then exiles cards from the top of their library until they exile a nonland card "
                + "with a different name than that spell. They may cast that card without paying its mana cost. "
                + "Then they put the exiled cards on the bottom of their library in a random order.";
    }

    private TibaltsTrickeryEffect(final TibaltsTrickeryEffect effect) {
        super(effect);
    }

    @Override
    public TibaltsTrickeryEffect copy() {
        return new TibaltsTrickeryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell == null) {
            return false;
        }
        Player controller = game.getPlayer(spell.getControllerId());
        game.getStack().counter(spell.getId(), source, game);
        if (controller == null) {
            return true;
        }
        int random = RandomUtil.nextInt(3) + 1;
        game.informPlayers(random + " was chosen at random");
        controller.millCards(random, source, game);
        Card cardToCast = null;
        Set<Card> cardsToExile = new HashSet<>();
        for (Card card : controller.getLibrary().getCards(game)) {
            cardsToExile.add(card);
            if (card != null && !card.isLand(game) && !card.sharesName(spell, game)) {
                cardToCast = card;
                break;
            }
        }
        controller.moveCardsToExile(cardsToExile, source, game, true, source.getSourceId(),
                CardUtil.createObjectRelatedWindowTitle(source, game, null));
        if (cardToCast != null) {
            if (controller.chooseUse(Outcome.PlayForFree, "Cast " + cardToCast.getLogName() + " for free?", source, game)) {
                game.getState().setValue("PlayFromNotOwnHandZone" + cardToCast.getId(), Boolean.TRUE);
                controller.cast(controller.chooseAbilityForCast(cardToCast, game, true),
                        game, true, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + cardToCast.getId(), null);
            }
        }
        ExileZone exile = game.getExile().getExileZone(source.getSourceId());
        if (exile != null) {
            controller.putCardsOnBottomOfLibrary(exile, game, source, false);
        }
        return true;
    }
}
