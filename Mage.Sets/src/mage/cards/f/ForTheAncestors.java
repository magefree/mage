package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForTheAncestors extends CardImpl {

    public ForTheAncestors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Choose a creature type. Look at the top six cards of your library. You may reveal any number of cards of the chosen type from among them and put the revealed cards into your hand. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new ForTheAncestorsEffect());

        // Flashback {3}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{G}")));
    }

    private ForTheAncestors(final ForTheAncestors card) {
        super(card);
    }

    @Override
    public ForTheAncestors copy() {
        return new ForTheAncestors(this);
    }
}

class ForTheAncestorsEffect extends OneShotEffect {

    ForTheAncestorsEffect() {
        super(Outcome.Benefit);
        staticText = "choose a creature type. Look at the top six cards of your library. " +
                "You may reveal any number of cards of the chosen type from among them and " +
                "put the revealed cards into your hand. Put the rest on the bottom of your library in a random order";
    }

    private ForTheAncestorsEffect(final ForTheAncestorsEffect effect) {
        super(effect);
    }

    @Override
    public ForTheAncestorsEffect copy() {
        return new ForTheAncestorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ChoiceCreatureType choice = new ChoiceCreatureType();
        player.choose(outcome, choice, game);
        SubType subType = SubType.fromString(choice.getChoice());
        FilterCard filter = new FilterCard(subType + " cards");
        filter.add(subType.getPredicate());
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 6));
        TargetCard target = new TargetCardInHand(0, Integer.MAX_VALUE, filter);
        player.choose(outcome, cards, target, source, game);
        Cards toHand = new CardsImpl(target.getTargets());
        player.revealCards(source, toHand, game);
        player.moveCards(toHand, Zone.HAND, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
