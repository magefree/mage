package mage.cards.d;

import java.util.HashSet;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author weirddan455
 */
public final class DreamsOfSteelAndOil extends CardImpl {

    public DreamsOfSteelAndOil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Target opponent reveals their hand. You choose an artifact or creature card from it, then choose an artifact or creature card from their graveyard. Exile the chosen cards.
        this.getSpellAbility().addEffect(new DreamsOfSteelAndOilEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private DreamsOfSteelAndOil(final DreamsOfSteelAndOil card) {
        super(card);
    }

    @Override
    public DreamsOfSteelAndOil copy() {
        return new DreamsOfSteelAndOil(this);
    }
}

class DreamsOfSteelAndOilEffect extends OneShotEffect {

    public DreamsOfSteelAndOilEffect() {
        super(Outcome.Discard);
        this.staticText = "Target opponent reveals their hand. You choose an artifact or creature card from it, then choose an artifact or creature card from their graveyard. Exile the chosen cards.";
    }

    private DreamsOfSteelAndOilEffect(final DreamsOfSteelAndOilEffect effect) {
        super(effect);
    }

    @Override
    public DreamsOfSteelAndOilEffect copy() {
        return new DreamsOfSteelAndOilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null) {
            return false;
        }

        HashSet<Card> toExile = new HashSet<>();
        opponent.revealCards(source, opponent.getHand(), game);
        FilterCard filter = new FilterCard("artifact or creature card form " + opponent.getName() + "'s hand");
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()));
        TargetCard target = new TargetCard(Zone.HAND, filter);
        target.setNotTarget(true);
        controller.chooseTarget(Outcome.Discard, opponent.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            toExile.add(card);
        }

        filter.setMessage("artifact or creature card from " + opponent.getName() + "'s graveyard");
        target = new TargetCard(Zone.GRAVEYARD, filter);
        target.setNotTarget(true);
        controller.chooseTarget(Outcome.Exile, opponent.getGraveyard(), target, source, game);
        card = game.getCard(target.getFirstTarget());
        if (card != null) {
            toExile.add(card);
        }

        controller.moveCards(toExile, Zone.EXILED, source, game);
        return true;
    }
}
