package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class GruesomeMenagerie extends CardImpl {

    public GruesomeMenagerie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Choose a creature card with converted mana cost 1 in your graveyard, then do the same for creature cards with converted mana costs 2 and 3. Return those cards to the battlefield.
        this.getSpellAbility().addEffect(new GruesomeMenagerieEffect());
    }

    private GruesomeMenagerie(final GruesomeMenagerie card) {
        super(card);
    }

    @Override
    public GruesomeMenagerie copy() {
        return new GruesomeMenagerie(this);
    }
}

class GruesomeMenagerieEffect extends OneShotEffect {

    private static final FilterCard filter1
            = new FilterCreatureCard("creature card with mana value 1");
    private static final FilterCard filter2
            = new FilterCreatureCard("creature card with mana value 2");
    private static final FilterCard filter3
            = new FilterCreatureCard("creature card with mana value 3");

    static {
        filter1.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, 1));
        filter2.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, 2));
        filter3.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, 3));
    }

    public GruesomeMenagerieEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose a creature card with mana value 1 "
                + "in your graveyard, then do the same for creature cards "
                + "with mana value 2 and 3. "
                + "Return those cards to the battlefield.";
    }

    public GruesomeMenagerieEffect(final GruesomeMenagerieEffect effect) {
        super(effect);
    }

    @Override
    public GruesomeMenagerieEffect copy() {
        return new GruesomeMenagerieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Target target;
        target = new TargetCardInYourGraveyard(filter1);
        target.setNotTarget(true);
        if (player.choose(outcome, target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                cards.add(card);
            }
        }
        target = new TargetCardInYourGraveyard(filter2);
        target.setNotTarget(true);
        if (player.choose(outcome, target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                cards.add(card);
            }
        }
        target = new TargetCardInYourGraveyard(filter3);
        target.setNotTarget(true);
        if (player.choose(outcome, target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                cards.add(card);
            }
        }
        return player.moveCards(cards, Zone.BATTLEFIELD, source, game);
    }
}
