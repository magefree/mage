package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ReparteeAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.common.SourceHasCountersCondition;
import mage.abilities.effects.common.PutSavedPermanentCountersTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScoldingAdministrator extends CardImpl {

    public ScoldingAdministrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Repartee -- Whenever you cast an instant or sorcery spell that targets a creature, put a +1/+1 counter on this creature.
        this.addAbility(new ReparteeAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));

        // When this creature dies, if it had counters on it, put those counters on up to one target creature.
        Ability ability = new DiesSourceTriggeredAbility(new PutSavedPermanentCountersTargetEffect("permanentLeftBattlefield"))
                .withTriggerCondition(SourceHasCountersCondition.instance) // don't want to check intervening if on resolution
                .setTriggerPhrase("When {this} dies, ");
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private ScoldingAdministrator(final ScoldingAdministrator card) {
        super(card);
    }

    @Override
    public ScoldingAdministrator copy() {
        return new ScoldingAdministrator(this);
    }
}
