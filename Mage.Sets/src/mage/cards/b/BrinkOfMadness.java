package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.discard.DiscardHandTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class BrinkOfMadness extends CardImpl {

    public BrinkOfMadness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // At the beginning of your upkeep, if you have no cards in hand, sacrifice Brink of Madness and target opponent discards their hand.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceEffect()).withInterveningIf(HellbentCondition.instance);
        ability.addEffect(new DiscardHandTargetEffect().concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private BrinkOfMadness(final BrinkOfMadness card) {
        super(card);
    }

    @Override
    public BrinkOfMadness copy() {
        return new BrinkOfMadness(this);
    }

}
