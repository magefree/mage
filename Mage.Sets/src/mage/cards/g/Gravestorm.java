
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class Gravestorm extends CardImpl {

    public Gravestorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}{B}{B}");

        // At the beginning of your upkeep, target opponent may exile a card from their graveyard. If that player doesn't, you may draw a card.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new GravestormEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private Gravestorm(final Gravestorm card) {
        super(card);
    }

    @Override
    public Gravestorm copy() {
        return new Gravestorm(this);
    }
}

class GravestormEffect extends OneShotEffect {

    public GravestormEffect() {
        super(Outcome.Exile);
        this.staticText = "At the beginning of your upkeep, target opponent may exile a card from their graveyard. If that player doesn't, you may draw a card.";
    }

    private GravestormEffect(final GravestormEffect effect) {
        super(effect);
    }

    @Override
    public GravestormEffect copy() {
        return new GravestormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null && you != null) {
            FilterCard filter = new FilterCard("card from your graveyard");
            filter.add(new OwnerIdPredicate(targetPlayer.getId()));
            TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
            boolean opponentChoosesExile = targetPlayer.chooseUse(Outcome.Exile, "Exile a card from your graveyard?", source, game);
            boolean opponentExilesACard = false;
            if (opponentChoosesExile && targetPlayer.chooseTarget(Outcome.Exile, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {                    
                    opponentExilesACard = targetPlayer.moveCardToExileWithInfo(card, null, "", source, game, Zone.GRAVEYARD, true);
                }
            }
            
            if (!opponentExilesACard) {                
                if (you.chooseUse(Outcome.DrawCard, "Draw a card?", source, game)) {
                    you.drawCards(1, source, game);
                }
            }
            return true;
        }
        return false;
    }
}