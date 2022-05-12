package mage.cards.l;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.command.emblems.LukeSkywalkerEmblem;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class LukeSkywalkerTheLastJedi extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature permanent");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public LukeSkywalkerTheLastJedi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{W}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.LUKE);
        this.setStartingLoyalty(3);

        // +1: Put two +1/+1 counters on up to one target creature.
        Ability ability1 = new LoyaltyAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)), 2);
        ability1.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability1);

        // -3: Put target noncreature permanent on top of its owner's library.
        Ability ability2 = new LoyaltyAbility(new PutOnLibraryTargetEffect(true), -3);
        ability2.addTarget(new TargetPermanent(filter));
        this.addAbility(ability2);

        // -6: You get an emblem with "Prevent all damage that would be dealt to you during combat." Exile Luke Skywalker, the Last Jedi.
        Ability ability3 = new LoyaltyAbility(new GetEmblemEffect(new LukeSkywalkerEmblem()), -6);
        ability3.addEffect(new ExileSourceEffect());
        this.addAbility(ability3);
    }

    private LukeSkywalkerTheLastJedi(final LukeSkywalkerTheLastJedi card) {
        super(card);
    }

    @Override
    public LukeSkywalkerTheLastJedi copy() {
        return new LukeSkywalkerTheLastJedi(this);
    }
}
