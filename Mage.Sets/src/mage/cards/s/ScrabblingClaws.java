
package mage.cards.s;

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
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ScrabblingClaws extends CardImpl {

    public ScrabblingClaws(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {tap}: Target player exiles a card from their graveyard.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ScrabblingClawsEffect(), new
                TapSourceCost()
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // {1}, Sacrifice Scrabbling Claws: Exile target card from a graveyard. Draw a card.
        ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ExileTargetEffect(),
                new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInGraveyard());
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(ability);
    }

    private ScrabblingClaws(final ScrabblingClaws card) {
        super(card);
    }

    @Override
    public ScrabblingClaws copy() {
        return new ScrabblingClaws(this);
    }
}

class ScrabblingClawsEffect extends OneShotEffect {

    ScrabblingClawsEffect() {
        super(Outcome.Exile);
        this.staticText = "Target player exiles a card from their graveyard";
    }

    private ScrabblingClawsEffect(final ScrabblingClawsEffect effect) {
        super(effect);
    }

    @Override
    public ScrabblingClawsEffect copy() {
        return new ScrabblingClawsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer == null) {
            return false;
        }
        FilterCard filter = new FilterCard("card from your graveyard");
        filter.add(new OwnerIdPredicate(targetPlayer.getId()));
        TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
        if (!targetPlayer.chooseTarget(Outcome.Exile, target, source, game)) {
            return false;
        }
        Card card = game.getCard(target.getFirstTarget());
        return card != null && targetPlayer.moveCards(card, Zone.EXILED, source, game);
    }
}
