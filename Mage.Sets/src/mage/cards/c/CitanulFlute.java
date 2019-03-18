
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Backfir3
 */
public final class CitanulFlute extends CardImpl {

    public CitanulFlute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // {X}, {T}: Search your library for a creature card with converted mana cost X or less, reveal it,
        // and put it into your hand. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CitanulFluteSearchEffect(), new ManaCostsImpl("{X}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public CitanulFlute(final CitanulFlute card) {
        super(card);
    }

    @Override
    public CitanulFlute copy() {
        return new CitanulFlute(this);
    }
}

class CitanulFluteSearchEffect extends OneShotEffect {

    public CitanulFluteSearchEffect() {
        super(Outcome.DrawCard);
	staticText = "Search your library for a creature card with converted mana cost X or less, reveal it, and put it into your hand. Then shuffle your library";
    }

    public CitanulFluteSearchEffect(final CitanulFluteSearchEffect effect) {
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
		
        FilterCard filter = new FilterCard("creature card with converted mana cost X or less");
        filter.add(new CardTypePredicate(CardType.CREATURE));
        //Set the mana cost one higher to 'emulate' a less than or equal to comparison.
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, source.getManaCostsToPay().getX() + 1));
		
	TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (player.searchLibrary(target, game)) {
            if (!target.getTargets().isEmpty()) {
                Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
                Cards cards = new CardsImpl();
                if (card != null){
                    card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                    cards.add(card);
                }
                String name = "Reveal";
                Card sourceCard = game.getCard(source.getSourceId());
                if (sourceCard != null) {
                    name = sourceCard.getName();
                }
                player.revealCards(name, cards, game);
            }
            player.shuffleLibrary(source, game);
            return true;
        }
	player.shuffleLibrary(source, game);
        return false;
    }
}
