package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author weirddan455
 */
public final class ForebodingStatue extends CardImpl {

    public ForebodingStatue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.f.ForsakenThresher.class;

        // {T}: Add one mana of any color. Put an omen counter on Foreboding Statue.
        Ability ability = new AnyColorManaAbility();
        ability.addEffect(new AddCountersSourceEffect(CounterType.OMEN.createInstance()));
        this.addAbility(ability);

        // At the beginning of your end step, if there are three or more omen counters on Foreboding Statue, uptap it, then transform it.
        this.addAbility(new TransformAbility());
        ability = new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new UntapSourceEffect().setText("untap it,"), TargetController.YOU,
                new SourceHasCounterCondition(CounterType.OMEN, 3), false
        ).setTriggerPhrase("At the beginning of your end step, if there are three or more omen counters on {this}, ");
        ability.addEffect(new TransformSourceEffect().setText("then transform it"));
        this.addAbility(ability);
    }

    private ForebodingStatue(final ForebodingStatue card) {
        super(card);
    }

    @Override
    public ForebodingStatue copy() {
        return new ForebodingStatue(this);
    }
}
