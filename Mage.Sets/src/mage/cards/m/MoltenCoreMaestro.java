package mage.cards.m;

import mage.MageInt;
import mage.Mana;
import mage.abilities.abilityword.OpusAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoltenCoreMaestro extends CardImpl {

    public MoltenCoreMaestro(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Opus -- Whenever you cast an instant or sorcery spell, put a +1/+1 counter on this creature. If five or more mana was spent to cast that spell, add an amount of {R} equal to this creature's power.
        this.addAbility(new OpusAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new DynamicManaEffect(
                        Mana.RedMana(1), SourcePermanentPowerValue.NOT_NEGATIVE,
                        "add an amount of {R} equal to this creature's power"
                ), null, false
        ));
    }

    private MoltenCoreMaestro(final MoltenCoreMaestro card) {
        super(card);
    }

    @Override
    public MoltenCoreMaestro copy() {
        return new MoltenCoreMaestro(this);
    }
}
