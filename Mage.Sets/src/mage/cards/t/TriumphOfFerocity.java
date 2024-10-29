package mage.cards.t;

import mage.abilities.condition.common.ControlsCreatureGreatestPowerCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author noxx
 */
public final class TriumphOfFerocity extends CardImpl {

    public TriumphOfFerocity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // At the beginning of your upkeep, draw a card if you control the creature with the greatest power or tied for the greatest power.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), ControlsCreatureGreatestPowerCondition.instance
        )));
    }

    private TriumphOfFerocity(final TriumphOfFerocity card) {
        super(card);
    }

    @Override
    public TriumphOfFerocity copy() {
        return new TriumphOfFerocity(this);
    }
}
