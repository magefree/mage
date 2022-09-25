package mage.cards.p;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class PickTheBrain extends CardImpl {

    public PickTheBrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent reveals their hand. You choose a nonland card from it and exile that card.
        // <i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, search that player's graveyard, hand, and library for any number of cards with the same name as the exiled card, exile those cards, then that player shuffles their library.
        this.getSpellAbility().addEffect(new PickTheBrainEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addHint(CardTypesInGraveyardHint.YOU);
    }

    private PickTheBrain(final PickTheBrain card) {
        super(card);
    }

    @Override
    public PickTheBrain copy() {
        return new PickTheBrain(this);
    }
}

class PickTheBrainEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    public PickTheBrainEffect() {
        super(true, "that card's controller", "all cards with the same name as that card");
        this.staticText = "Target opponent reveals their hand. You choose a nonland card from it and exile that card.<br><br>"
                + "<i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, "
                + "search that player's graveyard, hand, and library for any number of cards "
                + "with the same name as the exiled card, exile those cards, then that player shuffles";
    }

    public PickTheBrainEffect(final PickTheBrainEffect effect) {
        super(effect);
    }

    @Override
    public PickTheBrainEffect copy() {
        return new PickTheBrainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (opponent != null && controller != null) {
            if (!opponent.getHand().isEmpty()) {
                opponent.revealCards("Exile " + StaticFilters.FILTER_CARD_A_NON_LAND.getMessage(), opponent.getHand(), game);
                TargetCard target = new TargetCard(Zone.HAND, StaticFilters.FILTER_CARD_A_NON_LAND);
                if (controller.choose(Outcome.Exile, opponent.getHand(), target, game)) {
                    Card card = opponent.getHand().get(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, null, "", source, game, Zone.HAND, true);

                        // Check the Delirium condition
                        if (!DeliriumCondition.instance.apply(game, source)) {
                            return true;
                        }
                        return this.applySearchAndExile(game, source, card.getName(), opponent.getId());
                    }
                }
            }
        }
        return false;
    }
}
