package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.TheRingTemptsYouTriggeredAbility;
import mage.abilities.condition.common.CreatureDiedControlledCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Susucr
 */
public final class SmeagolHelpfulGuide extends CardImpl {

    public SmeagolHelpfulGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // At the beginning of your end step, if a creature died under your control this turn, the Ring tempts you.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
            new TheRingTemptsYouEffect(), TargetController.YOU,
            CreatureDiedControlledCondition.instance, false
        ).addHint(CreatureDiedControlledCondition.getHint()));

        // Whenever the Ring tempts you, target opponent reveals cards from the top of their library until they reveal
        // a land card. Put that card onto the battlefield tapped under your control and the rest into their graveyard.
        Ability ability = new TheRingTemptsYouTriggeredAbility(new SmeagolHelpfulGuideEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private SmeagolHelpfulGuide(final SmeagolHelpfulGuide card) {
        super(card);
    }

    @Override
    public SmeagolHelpfulGuide copy() {
        return new SmeagolHelpfulGuide(this);
    }
}

class SmeagolHelpfulGuideEffect extends OneShotEffect {

    SmeagolHelpfulGuideEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "target opponent reveals cards from the top of their library until they reveal " +
            "a land card. Put that card onto the battlefield tapped under your control " +
            "and the rest into their graveyard.";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (opponent == null || controller == null) {
            return false;
        }

        CardsImpl revealed = new CardsImpl();
        Card land = null;
        for (Card card : opponent.getLibrary().getCards(game)) {
            if (card != null) {
                revealed.add(card);
                if (card.isLand(game)) {
                    land = card;
                    break;
                }
            }
        }

        // Every card is revealed, land included.
        opponent.revealCards(source, revealed, game);

        // If there was a land card, the source's controller puts in on the battlefield.
        if(land != null) {
            revealed.remove(land);
            controller.moveCards(land, Zone.BATTLEFIELD, source, game);
        }

        // Rest of the revealed cards are put into the opponent's graveyard.
        opponent.moveCards(revealed, Zone.GRAVEYARD, source, game);
        return true;
    }

    private SmeagolHelpfulGuideEffect(final SmeagolHelpfulGuideEffect effect) {
        super(effect);
    }

    @Override
    public SmeagolHelpfulGuideEffect copy() {
        return new SmeagolHelpfulGuideEffect(this);
    }
}
