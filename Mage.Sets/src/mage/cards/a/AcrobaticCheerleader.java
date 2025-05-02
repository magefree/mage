package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.abilityword.SurvivalAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author markort147
 */
public final class AcrobaticCheerleader extends CardImpl {

    public AcrobaticCheerleader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SURVIVOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Survival -- At the beginning of your second main phase, if Acrobatic Cheerleader is tapped, put a flying counter on it. This ability triggers only once.
        TriggeredAbility ability = new SurvivalAbility(new AddCountersSourceEffect(CounterType.FLYING.createInstance()))
                .setTriggersLimitEachGame(1)
                .withRuleTextReplacement(true);
        this.addAbility(ability);
    }

    private AcrobaticCheerleader(final AcrobaticCheerleader card) {
        super(card);
    }

    @Override
    public AcrobaticCheerleader copy() {
        return new AcrobaticCheerleader(this);
    }
}
