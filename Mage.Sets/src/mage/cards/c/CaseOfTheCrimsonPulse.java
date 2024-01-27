package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.CaseAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.condition.common.SolvedSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.hint.Hint;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * Case of the Crimson Pulse {2}{R}
 * Enchantment - Case
 * When this Case enters the battlefield, discard a card, then draw two cards.
 * To solve -- You have no cards in hand.
 * Solved -- At the beginning of your upkeep, discard your hand, then draw two cards.
 *
 * @author DominionSpy
 */
public final class CaseOfTheCrimsonPulse extends CardImpl {

    public CaseOfTheCrimsonPulse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        this.subtype.add(SubType.CASE);

        // When this Case enters the battlefield, discard a card, then draw two cards.
        Ability initialAbility = new EntersBattlefieldTriggeredAbility(new DiscardControllerEffect(1));
        initialAbility.addEffect(new DrawCardSourceControllerEffect(2).setText(", then draw two cards."));
        // To solve -- You have no cards in hand.
        Condition toSolveCondition = new CardsInHandCondition(ComparisonType.EQUAL_TO, 0);
        // Solved -- At the beginning of your upkeep, discard your hand, then draw two cards.
        Ability solvedAbility = new ConditionalTriggeredAbility(new BeginningOfUpkeepTriggeredAbility(
                new DiscardHandControllerEffect(), TargetController.YOU, false),
                SolvedSourceCondition.SOLVED, null);
        solvedAbility.addEffect(new DrawCardSourceControllerEffect(2).setText(", then draw two cards."));

        this.addAbility(new CaseAbility(initialAbility, toSolveCondition, solvedAbility)
                .addHint(CaseOfTheCrimsonPulseHint.instance));
    }

    private CaseOfTheCrimsonPulse(final CaseOfTheCrimsonPulse card) {
        super(card);
    }

    @Override
    public CaseOfTheCrimsonPulse copy() {
        return new CaseOfTheCrimsonPulse(this);
    }
}

enum CaseOfTheCrimsonPulseHint implements Hint {
    instance;

    @Override
    public CaseOfTheCrimsonPulseHint copy() {
        return this;
    }

    @Override
    public String getText(Game game, Ability ability) {
        Permanent permanent = game.getPermanent(ability.getSourceId());
        Player controller = game.getPlayer(ability.getControllerId());
        if (permanent == null || controller == null) {
            return "";
        }

        if (permanent.isSolved()) {
            return "Case is solved";
        }
        int handSize = controller.getHand().size();
        StringBuilder sb = new StringBuilder("Case is unsolved. Cards in hand: ");
        sb.append(handSize);
        sb.append(" (need 0).");
        if (handSize == 0 && game.isActivePlayer(ability.getControllerId())) {
            sb.append(" Case will be solved at the end step.");
        }
        return sb.toString();
    }
}
