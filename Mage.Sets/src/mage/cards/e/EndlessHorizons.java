
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.SearchEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class EndlessHorizons extends CardImpl {

    public EndlessHorizons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");

        // When Endless Horizons enters the battlefield, search your library for any number of Plains cards and exile them. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EndlessHorizonsEffect(), false));

        // At the beginning of your upkeep, you may put a card you own exiled with Endless Horizons into your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new EndlessHorizonsEffect2(), TargetController.YOU, true));

    }

    public EndlessHorizons(final EndlessHorizons card) {
        super(card);
    }

    @Override
    public EndlessHorizons copy() {
        return new EndlessHorizons(this);
    }
}

class EndlessHorizonsEffect extends SearchEffect {

    private static final FilterLandCard filter = new FilterLandCard("Plains card");

    static {
        filter.add(new SubtypePredicate(SubType.PLAINS));
    }

    public EndlessHorizonsEffect() {
        super(new TargetCardInLibrary(0, Integer.MAX_VALUE, filter), Outcome.Neutral);
        this.staticText = "search your library for any number of Plains cards and exile them. Then shuffle your library";
    }

    public EndlessHorizonsEffect(final EndlessHorizonsEffect effect) {
        super(effect);
    }

    @Override
    public EndlessHorizonsEffect copy() {
        return new EndlessHorizonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            if (you.searchLibrary(target, game)) {
                UUID exileZone = CardUtil.getCardExileZoneId(game, source);
                if (!target.getTargets().isEmpty()) {
                    for (UUID cardId : target.getTargets()) {
                        Card card = you.getLibrary().getCard(cardId, game);
                        if (card != null) {
                            card.moveToExile(exileZone, "Endless Horizons", source.getSourceId(), game);
                        }
                    }
                }
            }
            you.shuffleLibrary(source, game);
            return true;

        }
        return false;
    }
}

class EndlessHorizonsEffect2 extends OneShotEffect {

    public EndlessHorizonsEffect2() {
        super(Outcome.ReturnToHand);
        this.staticText = "you may put a card you own exiled with {this} into your hand";
    }

    public EndlessHorizonsEffect2(final EndlessHorizonsEffect2 effect) {
        super(effect);
    }

    @Override
    public EndlessHorizonsEffect2 copy() {
        return new EndlessHorizonsEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ExileZone exZone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
            if (exZone != null) {
                Card card = null;
                if (exZone.size() > 1) {
                    TargetCard target = new TargetCard(Zone.EXILED, new FilterCard());
                    controller.choose(outcome, exZone, target, game);
                    card = game.getCard(target.getFirstTarget());
                } else {
                    card = exZone.getRandom(game);
                }
                controller.moveCards(card, Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }

}
