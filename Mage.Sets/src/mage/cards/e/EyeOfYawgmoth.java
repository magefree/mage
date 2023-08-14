
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class EyeOfYawgmoth extends CardImpl {

    public EyeOfYawgmoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {3}, {T}, Sacrifice a creature: Reveal a number of cards from the top of your library equal to the sacrificed creature's power. Put one into your hand and exile the rest.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new EyeOfYawgmothEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability);
    }

    private EyeOfYawgmoth(final EyeOfYawgmoth card) {
        super(card);
    }

    @Override
    public EyeOfYawgmoth copy() {
        return new EyeOfYawgmoth(this);
    }
}

class EyeOfYawgmothEffect extends OneShotEffect {

    EyeOfYawgmothEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal a number of cards from the top of your library equal to the sacrificed creature's power. Put one into your hand and exile the rest";
    }

    EyeOfYawgmothEffect(final EyeOfYawgmothEffect effect) {
        super(effect);
    }

    @Override
    public EyeOfYawgmothEffect copy() {
        return new EyeOfYawgmothEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int power = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost && !((SacrificeTargetCost) cost).getPermanents().isEmpty()) {
                power = ((SacrificeTargetCost) cost).getPermanents().get(0).getPower().getValue();
                break;
            }
        }
        if (power > 0) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, power));
            controller.revealCards(source, cards, game);
            TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put into your hand"));
            if (controller.choose(Outcome.DrawCard, cards, target, source, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCards(card, Zone.HAND, source, game);
                    cards.remove(card);
                }
            }
            controller.moveCards(cards, Zone.EXILED, source, game);
        }
        return true;
    }
}
