
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author fireshoes
 */
public final class ScrabblingClaws extends CardImpl {

    public ScrabblingClaws(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {tap}: Target player exiles a card from their graveyard.
        Ability firstAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScrabblingClawsEffect(), new TapSourceCost());
        firstAbility.addTarget(new TargetPlayer());
        this.addAbility(firstAbility);
        // {1}, Sacrifice Scrabbling Claws: Exile target card from a graveyard. Draw a card.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new SacrificeSourceCost());
        ability.addCost(new GenericManaCost(1));
        ability.addTarget(new TargetCardInGraveyard());
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(ability);
    }

    public ScrabblingClaws(final ScrabblingClaws card) {
        super(card);
    }

    @Override
    public ScrabblingClaws copy() {
        return new ScrabblingClaws(this);
    }
}

class ScrabblingClawsEffect extends OneShotEffect {

    public ScrabblingClawsEffect() {
        super(Outcome.Exile);
        this.staticText = "Target player exiles a card from their graveyard";
    }

    public ScrabblingClawsEffect(final ScrabblingClawsEffect effect) {
        super(effect);
    }

    @Override
    public ScrabblingClawsEffect copy() {
        return new ScrabblingClawsEffect(this);
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
                    targetPlayer.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.GRAVEYARD, true);
                }
                return true;
            }
        }
        return false;
    }
}
