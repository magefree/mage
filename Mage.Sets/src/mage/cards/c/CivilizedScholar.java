package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class CivilizedScholar extends TransformingDoubleFacedCard {

    public CivilizedScholar(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ADVISOR}, "{2}{U}",
                "Homicidal Brute",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.MUTANT}, "R"
        );
        this.getLeftHalfCard().setPT(0, 1);
        this.getRightHalfCard().setPT(5, 1);

        // {tap}: Draw a card, then discard a card. If a creature card is discarded this way, untap Civilized Scholar, then transform it.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(
                new CivilizedScholarEffect(), new TapSourceCost()
        ));

        // Homicidal Brute
        // At the beginning of your end step, if Homicidal Brute didn't attack this turn, tap Homicidal Brute, then transform it.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new TapSourceEffect(), TargetController.YOU,
                HomicidalBruteCondition.instance, false
        );
        ability.addEffect(new TransformSourceEffect().setText(", then transform it"));
        this.getRightHalfCard().addAbility(ability, new AttackedThisTurnWatcher());
    }

    private CivilizedScholar(final CivilizedScholar card) {
        super(card);
    }

    @Override
    public CivilizedScholar copy() {
        return new CivilizedScholar(this);
    }
}

class CivilizedScholarEffect extends OneShotEffect {

    public CivilizedScholarEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw a card, then discard a card. If a creature card " +
                "is discarded this way, untap {this}, then transform it";
    }

    private CivilizedScholarEffect(final CivilizedScholarEffect effect) {
        super(effect);
    }

    @Override
    public CivilizedScholarEffect copy() {
        return new CivilizedScholarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.drawCards(1, source, game);
        Card card = player.discardOne(false, false, source, game);
        if (card == null || !card.isCreature(game)) {
            return true;
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            permanent.untap(game);
            permanent.transform(source, game);
        }
        return true;
    }
}

enum HomicidalBruteCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return !game
                .getState()
                .getWatcher(AttackedThisTurnWatcher.class)
                .checkIfAttacked(source.getSourcePermanentIfItStillExists(game), game);
    }

    @Override
    public String toString() {
        return "{this} didn't attack this turn";
    }
}
