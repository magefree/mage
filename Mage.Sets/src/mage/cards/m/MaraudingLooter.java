
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 *
 * @author TheElk801
 */
public final class MaraudingLooter extends CardImpl {

    public MaraudingLooter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Raid - At the beginning of your end step, if you attacked with a creature this turn, you may draw a card. If you do, discard a card.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(new DrawDiscardControllerEffect(1, 1, true), TargetController.YOU, false),
                RaidCondition.instance,
                "<i>Raid</i> &mdash; At the beginning of your end step, "
                + "if you attacked with a creature this turn, "
                + "you may draw a card. If you do, discard a card.");
        this.addAbility(ability, new PlayerAttackedWatcher());
    }

    public MaraudingLooter(final MaraudingLooter card) {
        super(card);
    }

    @Override
    public MaraudingLooter copy() {
        return new MaraudingLooter(this);
    }
}
