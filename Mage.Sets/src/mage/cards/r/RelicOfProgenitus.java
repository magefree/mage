
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author jonubuu
 */
public final class RelicOfProgenitus extends CardImpl {

    public RelicOfProgenitus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {tap}: Target player exiles a card from their graveyard.
        Ability firstAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RelicOfProgenitusEffect(), new TapSourceCost());
        firstAbility.addTarget(new TargetPlayer());
        this.addAbility(firstAbility);
        // {1}, Exile Relic of Progenitus: Exile all cards from all graveyards. Draw a card.
        Ability secondAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileGraveyardAllPlayersEffect(), new GenericManaCost(1));
        secondAbility.addCost(new ExileSourceCost());
        secondAbility.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(secondAbility);
    }

    private RelicOfProgenitus(final RelicOfProgenitus card) {
        super(card);
    }

    @Override
    public RelicOfProgenitus copy() {
        return new RelicOfProgenitus(this);
    }
}

class RelicOfProgenitusEffect extends OneShotEffect {

    public RelicOfProgenitusEffect() {
        super(Outcome.Exile);
        this.staticText = "Target player exiles a card from their graveyard";
    }

    private RelicOfProgenitusEffect(final RelicOfProgenitusEffect effect) {
        super(effect);
    }

    @Override
    public RelicOfProgenitusEffect copy() {
        return new RelicOfProgenitusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            FilterCard filter = new FilterCard("card from your graveyard");
            filter.add(new OwnerIdPredicate(targetPlayer.getId()));
            TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
            if (targetPlayer.chooseTarget(Outcome.Exile, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    targetPlayer.moveCardToExileWithInfo(card, null, "", source, game, Zone.GRAVEYARD, true);
                }
                return true;
            }
        }
        return false;
    }
}
