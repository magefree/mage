package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.PsionicAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.Counter;
import mage.counters.CounterType;

/**
 *
 * @author NinthWorld
 */
public final class Reaver extends CardImpl {

    public Reaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.PROTOSS);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Psionic 1
        this.addAbility(new PsionicAbility(1));

        // {1}, Remove a psi counter from Reaver: Reaver gets +2/+0 and gains first strike until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn), new GenericManaCost(1));
        ability.addCost(new RemoveCountersSourceCost(CounterType.PSI.createInstance(1)));
        ability.addEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn));
        this.addAbility(ability);

        // {T}: Put a psi counter on Reaver.
        this.addAbility(new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.PSI.createInstance(1)), new TapSourceCost()));
    }

    public Reaver(final Reaver card) {
        super(card);
    }

    @Override
    public Reaver copy() {
        return new Reaver(this);
    }
}
