package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PatientRebuilding extends CardImpl {

    public PatientRebuilding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // At the beginning of your upkeep, target opponent puts the top three cards of their library into their graveyard, then you draw a card for each land card put into that graveyard this way.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new PatientRebuildingEffect(),
                TargetController.YOU,
                false
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private PatientRebuilding(final PatientRebuilding card) {
        super(card);
    }

    @Override
    public PatientRebuilding copy() {
        return new PatientRebuilding(this);
    }
}

class PatientRebuildingEffect extends OneShotEffect {

    public PatientRebuildingEffect() {
        super(Outcome.DrawCard);
        this.staticText = "target opponent mills three cards, "
                + "then you draw a card for each land card put into that graveyard this way";
    }

    private PatientRebuildingEffect(final PatientRebuildingEffect effect) {
        super(effect);
    }

    @Override
    public PatientRebuildingEffect copy() {
        return new PatientRebuildingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || player == null) {
            return false;
        }
        int numberOfLandCards = 0;
        Set<Card> movedCards = player.millCards(3, source, game).getCards(game);
        for (Card card : movedCards) {
            if (card.isLand(game)) {
                numberOfLandCards++;
            }
        }
        if (numberOfLandCards > 0) {
            return controller.drawCards(numberOfLandCards, source, game) > 0;
        }
        return true;
    }

}
