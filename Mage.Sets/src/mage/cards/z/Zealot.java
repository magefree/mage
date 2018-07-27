package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
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
public final class Zealot extends CardImpl {

    public Zealot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");
        
        this.subtype.add(SubType.PROTOSS);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Psionic 1
        this.addAbility(new PsionicAbility(1));

        // {1}, Remove a psi counter from Zealot: Prevent all damage that would be dealt to Zealot this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventAllDamageToSourceEffect(Duration.EndOfTurn), new GenericManaCost(1));
        ability.addCost(new RemoveCountersSourceCost(CounterType.PSI.createInstance()));
        this.addAbility(ability);
    }

    public Zealot(final Zealot card) {
        super(card);
    }

    @Override
    public Zealot copy() {
        return new Zealot(this);
    }
}
