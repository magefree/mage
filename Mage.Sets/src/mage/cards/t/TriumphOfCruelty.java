package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.condition.common.ControlsCreatureGreatestPowerCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author noxx
 */
public final class TriumphOfCruelty extends CardImpl {

    public TriumphOfCruelty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // At the beginning of your upkeep, target opponent discards a card if you control the creature with the greatest power or tied for the greatest power.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new ConditionalOneShotEffect(
                new DiscardTargetEffect(1), ControlsCreatureGreatestPowerCondition.instance));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private TriumphOfCruelty(final TriumphOfCruelty card) {
        super(card);
    }

    @Override
    public TriumphOfCruelty copy() {
        return new TriumphOfCruelty(this);
    }
}
