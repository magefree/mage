
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth

 */
public final class Torture extends CardImpl {

    private static final String rule = "Testing rules";

    public Torture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // {1}{B}: Put a -1/-1 counter on enchanted creature.
        //this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersAttachedEffect(CounterType.M1M1.createInstance(), rule), new ManaCostsImpl<>("{[1}{B}}")));
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new AddCountersAttachedEffect(CounterType.M1M1.createInstance(),"enchanted creature"),
                new ManaCostsImpl<>("{1}{B}")));
    }

    private Torture(final Torture card) {
        super(card);
    }

    @Override
    public Torture copy() {
        return new Torture(this);
    }
}
