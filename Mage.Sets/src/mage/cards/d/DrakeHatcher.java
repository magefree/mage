package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.token.DrakeToken;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class DrakeHatcher extends CardImpl {

    public DrakeHatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());

        // Whenever this creature deals combat damage to a player, put that many incubation counters on it.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new AddCountersSourceEffect(
                        CounterType.INCUBATION.createInstance(),
                        SavedDamageValue.MANY, false
                ).setText("put that many incubation counters on it"), false
        ).setTriggerPhrase("Whenever this creature deals combat damage to a player, ");
        this.addAbility(ability);

        // Remove three incubation counters from this creature: Create a 2/2 blue Drake creature token with flying.
        this.addAbility(new SimpleActivatedAbility(new CreateTokenEffect(
                new DrakeToken()),
                new RemoveCountersSourceCost(CounterType.INCUBATION.createInstance(3)))
        );
    }

    private DrakeHatcher(final DrakeHatcher card) {
        super(card);
    }

    @Override
    public DrakeHatcher copy() {
        return new DrakeHatcher(this);
    }
}
