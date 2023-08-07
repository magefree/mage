
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public final class ThoughtpickerWitch extends CardImpl {

    public ThoughtpickerWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}, Sacrifice a creature: Look at the top two cards of target opponent's library, then exile one of them.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ThoughtpickerWitchEffect(), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private ThoughtpickerWitch(final ThoughtpickerWitch card) {
        super(card);
    }

    @Override
    public ThoughtpickerWitch copy() {
        return new ThoughtpickerWitch(this);
    }
}

class ThoughtpickerWitchEffect extends OneShotEffect {

    ThoughtpickerWitchEffect() {
        super(Outcome.Exile);
        this.staticText = "Look at the top two cards of target opponent's library, then exile one of them";
    }

    ThoughtpickerWitchEffect(final ThoughtpickerWitchEffect effect) {
        super(effect);
    }

    @Override
    public ThoughtpickerWitchEffect copy() {
        return new ThoughtpickerWitchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller != null && opponent != null) {
            Cards cards = new CardsImpl(opponent.getLibrary().getTopCards(game, 2));
            if (!cards.isEmpty()) {
                TargetCard target = new TargetCardInLibrary(new FilterCard("card to exile"));
                if (controller.choose(Outcome.Exile, cards, target, source, game)) {
                    Card card = cards.get(target.getFirstTarget(), game);
                    if (card != null) {
                        cards.remove(card);
                        opponent.moveCards(card, Zone.EXILED, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
