package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.*;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author Grath
 */
public final class AvelineDeGrandpre extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with deathtouch");

    static {
        filter.add(new AbilityPredicate(DeathtouchAbility.class));
    }

    public AvelineDeGrandpre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever a creature you control with deathtouch deals combat damage to a player, put that many +1/+1 counters on that creature.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(), SavedDamageValue.MANY),
                filter,
                false, SetTargetPointer.PERMANENT, true
        ));

        // Disguise {B}{G}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{B}{G}")));

    }

    private AvelineDeGrandpre(final AvelineDeGrandpre card) {
        super(card);
    }

    @Override
    public AvelineDeGrandpre copy() {
        return new AvelineDeGrandpre(this);
    }
}
