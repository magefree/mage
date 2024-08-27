
package mage.cards.t;

import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author cbt33
 */
public final class ThermalBlast extends CardImpl {

    public ThermalBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Thermal Blast deals 3 damage to target creature.
        // Threshold - Thermal Blast deals 5 damage to that creature instead if seven or more cards are in your graveyard.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(5), new DamageTargetEffect(3), ThresholdCondition.instance,
                "{this} deals 3 damage to target creature.<br>" + AbilityWord.THRESHOLD.formatWord() +
                        "{this} deals 5 damage instead if seven or more cards are in your graveyard."
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ThermalBlast(final ThermalBlast card) {
        super(card);
    }

    @Override
    public ThermalBlast copy() {
        return new ThermalBlast(this);
    }
}
