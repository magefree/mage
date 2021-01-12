package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoggedPursuit extends CardImpl {

    public DoggedPursuit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // At the beginning of your end step, each opponent loses 1 life and you gain 1 life.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new LoseLifeOpponentsEffect(1), TargetController.YOU, false
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private DoggedPursuit(final DoggedPursuit card) {
        super(card);
    }

    @Override
    public DoggedPursuit copy() {
        return new DoggedPursuit(this);
    }
}
