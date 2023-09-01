package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class ForebodingStatue extends TransformingDoubleFacedCard {

    public ForebodingStatue(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.CONSTRUCT}, "{3}",
                "Forsaken Thresher",
                new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.CONSTRUCT}, ""
        );
        this.getLeftHalfCard().setPT(1, 2);
        this.getRightHalfCard().setPT(5, 5);

        // {T}: Add one mana of any color. Put an omen counter on Foreboding Statue.
        Ability ability = new AnyColorManaAbility();
        ability.addEffect(new AddCountersSourceEffect(CounterType.OMEN.createInstance()));
        this.getLeftHalfCard().addAbility(ability);

        // At the beginning of your end step, if there are three or more omen counters on Foreboding Statue, uptap it, then transform it.
        ability = new BeginningOfEndStepTriggeredAbility(
                new UntapSourceEffect().setText("untap it,"), TargetController.YOU,
                new SourceHasCounterCondition(CounterType.OMEN, 3), false
        ).setTriggerPhrase("At the beginning of your end step, if there are three or more omen counters on {this}, ");
        ability.addEffect(new TransformSourceEffect().setText("then transform it"));
        this.getLeftHalfCard().addAbility(ability);

        // Forsaken Thresher
        // At the beginning of your precombat main phase, add one mana of any color.
        this.getRightHalfCard().addAbility(new BeginningOfPreCombatMainTriggeredAbility(
                new AddManaOfAnyColorEffect(), TargetController.YOU, false
        ));
    }

    private ForebodingStatue(final ForebodingStatue card) {
        super(card);
    }

    @Override
    public ForebodingStatue copy() {
        return new ForebodingStatue(this);
    }
}
