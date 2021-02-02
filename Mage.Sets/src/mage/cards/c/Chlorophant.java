
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;

/**
 *
 * @author cbt33
 */
public final class Chlorophant extends CardImpl {

    public Chlorophant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, you may put a +1/+1 counter on Chlorophant.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), TargetController.YOU, true));
        // Threshold - As long as seven or more cards are in your graveyard, Chlorophant has "At the beginning of your upkeep, you may put another +1/+1 counter on Chlorophant."
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance());
        effect.setText("At the beginning of your upkeep, you may put another +1/+1 counter on {this}.");
        Ability gainedAbility = new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, true);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(gainedAbility, Duration.WhileOnBattlefield), new CardsInControllerGraveyardCondition(7),
            "As long as seven or more cards are in your graveyard, {this} has \"At the beginning of your upkeep, you may put another +1/+1 counter on {this}.\""));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private Chlorophant(final Chlorophant card) {
        super(card);
    }

    @Override
    public Chlorophant copy() {
        return new Chlorophant(this);
    }
}
