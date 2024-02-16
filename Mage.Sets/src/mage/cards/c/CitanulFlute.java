package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Backfir3
 */
public final class CitanulFlute extends CardImpl {

    public CitanulFlute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // {X}, {T}: Search your library for a creature card with converted mana cost X or less, reveal it,
        // and put it into your hand. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(new CitanulFluteSearchEffect(), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CitanulFlute(final CitanulFlute card) {
        super(card);
    }

    @Override
    public CitanulFlute copy() {
        return new CitanulFlute(this);
    }
}

class CitanulFluteSearchEffect extends OneShotEffect {

    CitanulFluteSearchEffect() {
        super(Outcome.DrawCard);
        staticText = "Search your library for a creature card with mana value X or less, " +
                "reveal it, put it into your hand, then shuffle";
    }

    private CitanulFluteSearchEffect(final CitanulFluteSearchEffect effect) {
        super(effect);
    }

    @Override
    public CitanulFluteSearchEffect copy() {
        return new CitanulFluteSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        FilterCard filter = new FilterCreatureCard("creature card with mana value X or less");
        //Set the mana cost one higher to 'emulate' a less than or equal to comparison.
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, source.getManaCostsToPay().getX() + 1));

        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        if (card != null) {
            player.revealCards(source, new CardsImpl(card), game);
            player.moveCards(card, Zone.HAND, source, game);
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}
