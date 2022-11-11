
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 * @author Loki
 */
public final class SturdyHatchling extends CardImpl {

    private static final FilterSpell filterGreenSpell = new FilterSpell("a green spell");
    private static final FilterSpell filterBlueSpell = new FilterSpell("a blue spell");

    static {
        filterGreenSpell.add(new ColorPredicate(ObjectColor.GREEN));
        filterBlueSpell.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public SturdyHatchling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G/U}");
        this.subtype.add(SubType.ELEMENTAL);


        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        
        // Sturdy Hatchling enters the battlefield with four -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(4)),"with four -1/-1 counters on it"));
        // {G/U}: Sturdy Hatchling gains shroud until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(ShroudAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{G/U}")));
        // Whenever you cast a green spell, remove a -1/-1 counter from Sturdy Hatchling.
        this.addAbility(new SpellCastControllerTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance(1)), filterGreenSpell, false));
        // Whenever you cast a blue spell, remove a -1/-1 counter from Sturdy Hatchling.
        this.addAbility(new SpellCastControllerTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance(1)), filterBlueSpell, false));
    }

    private SturdyHatchling(final SturdyHatchling card) {
        super(card);
    }

    @Override
    public SturdyHatchling copy() {
        return new SturdyHatchling(this);
    }
}
