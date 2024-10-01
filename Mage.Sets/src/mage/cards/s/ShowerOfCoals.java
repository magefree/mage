
package mage.cards.s;

import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author cbt33, LevelX2 (Kirtar's Wrath)
 */
public final class ShowerOfCoals extends CardImpl {

    public ShowerOfCoals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Shower of Coals deals 2 damage to each of up to three target creatures and/or players.
        // Threshold - Shower of Coals deals 4 damage to each of those creatures and/or players instead if seven or more cards are in your graveyard.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(4), new DamageTargetEffect(2),
                ThresholdCondition.instance, "{this} deals 2 damage to each of up to three targets.<br>" +
                AbilityWord.THRESHOLD.formatWord() + "{this} deals 4 damage to each of those permanents " +
                "and/or players instead if seven or more cards are in your graveyard."
        ));
        this.getSpellAbility().addTarget(new TargetAnyTarget(0, 3));
    }

    private ShowerOfCoals(final ShowerOfCoals card) {
        super(card);
    }

    @Override
    public ShowerOfCoals copy() {
        return new ShowerOfCoals(this);
    }
}
