package mage.cards.c;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CrimeNovelist extends CardImpl {

    public CrimeNovelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you sacrifice an artifact, put a +1/+1 counter on Crime Novelist and add {R}.
        Ability ability = new SacrificePermanentTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_PERMANENT_ARTIFACT_AN
        );
        ability.addEffect(new BasicManaEffect(Mana.RedMana(1)).concatBy("and"));
        this.addAbility(ability);
    }

    private CrimeNovelist(final CrimeNovelist card) {
        super(card);
    }

    @Override
    public CrimeNovelist copy() {
        return new CrimeNovelist(this);
    }
}
