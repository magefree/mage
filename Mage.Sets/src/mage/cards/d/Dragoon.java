package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.PsionicAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author NinthWorld
 */
public final class Dragoon extends CardImpl {

    public Dragoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");
        
        this.subtype.add(SubType.PROTOSS);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Psionic 1
        this.addAbility(new PsionicAbility(1));

        // {1}, Remove a psi counter from Dragoon: Dragoon gains first strike until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), new GenericManaCost(1));
        ability.addCost(new RemoveCountersSourceCost(CounterType.PSI.createInstance(1)));
        this.addAbility(ability);
    }

    public Dragoon(final Dragoon card) {
        super(card);
    }

    @Override
    public Dragoon copy() {
        return new Dragoon(this);
    }
}
