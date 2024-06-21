package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.condition.common.CollectedEvidenceCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.abilities.keyword.CollectEvidenceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class DeadlyCoverUp extends CardImpl {

    public DeadlyCoverUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");


        // As an additional cost to cast this spell, you may collect evidence 6.
        this.addAbility(new CollectEvidenceAbility(8));

        // Destroy all creatures. If evidence was collected, exile a card from an opponent's graveyard. Then search its owner's graveyard, hand, and library for any number of cards with that name and exile them. That player shuffles, then draws a card for each card exiled from their hand this way.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DeadlyCoverUpEffect(), CollectedEvidenceCondition.instance,
                "If evidence was collected, exile a card from an opponent's graveyard. Then search its owner's "
                        + "graveyard, hand, and library for any number of cards with that name and exile them. "
                        + "That player shuffles, then draws a card for each card exiled from their hand this way."));
    }

    private DeadlyCoverUp(final DeadlyCoverUp card) {
        super(card);
    }

    @Override
    public DeadlyCoverUp copy() {
        return new DeadlyCoverUp(this);
    }
}

class DeadlyCoverUpEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    DeadlyCoverUpEffect() {
        super(true, "its owner's", "any number of cards with the same name as that card", true);
        this.staticText = "exile a card from an opponent's graveyard. Then search its owner's "
                + "graveyard, hand, and library for any number of cards with that name and exile them. "
                + "That player shuffles, then draws a card for each card exiled from their hand this way.";
    }

    private DeadlyCoverUpEffect(final DeadlyCoverUpEffect effect) {
        super(effect);
    }

    @Override
    public DeadlyCoverUpEffect copy() {
        return new DeadlyCoverUpEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        TargetCardInOpponentsGraveyard target = new TargetCardInOpponentsGraveyard(StaticFilters.FILTER_CARD);
        target.withNotTarget(true);
        if (player != null && player.chooseTarget(Outcome.Exile, target, source, game)) {
            Card cardToExile = game.getCard(target.getFirstTarget());
            if (cardToExile == null) {
                return false;
            }
            player.moveCards(cardToExile, Zone.EXILED, source, game);
            this.applySearchAndExile(game, source, cardToExile.getName(), cardToExile.getOwnerId());
        }
        return true;
    }
}
