package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.CaseAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.condition.common.SolvedSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.hint.common.CaseSolvedHint;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;
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
        // Solved -- At the beginning of your upkeep, discard your hand, then draw two cards.
        Ability solvedAbility = new ConditionalTriggeredAbility(new BeginningOfUpkeepTriggeredAbility(
                new DiscardHandControllerEffect(), TargetController.YOU, false),
                SolvedSourceCondition.SOLVED, null);
        solvedAbility.addEffect(new DrawCardSourceControllerEffect(2).concatBy(", then"));

        this.addAbility(new CaseAbility(initialAbility, HellbentCondition.instance, solvedAbility)
                .addHint(new CaseOfTheCrimsonPulseHint()));
    }

    private CaseOfTheCrimsonPulse(final CaseOfTheCrimsonPulse card) {
        super(card);
    }

    @Override
    public CaseOfTheCrimsonPulse copy() {
        return new CaseOfTheCrimsonPulse(this);
    }
}

class CaseOfTheCrimsonPulseHint extends CaseSolvedHint {

    CaseOfTheCrimsonPulseHint() {
        super(HellbentCondition.instance);
    }

    private CaseOfTheCrimsonPulseHint(final CaseOfTheCrimsonPulseHint hint) {
        super(hint);
    }

    @Override
    public CaseOfTheCrimsonPulseHint copy() {
        return new CaseOfTheCrimsonPulseHint(this);
    }

    @Override
    public String getConditionText(Game game, Ability ability) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller == null) {
            return "";
        }
        int handSize = controller.getHand().size();
        return "Cards in hand: " + handSize + " (need 0).";
    }
}
