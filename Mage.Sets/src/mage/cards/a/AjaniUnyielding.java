
package mage.cards.a;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.ExileAndGainLifeEqualPowerTargetEffect;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author JRHerlehy
 */
public final class AjaniUnyielding extends CardImpl {

    private static final FilterPermanentCard nonlandPermanentFilter = new FilterPermanentCard("nonland permanent cards");
    private static final FilterPlaneswalkerPermanent planeswalkerFilter = new FilterPlaneswalkerPermanent("other planeswalker you control");

    static {
        nonlandPermanentFilter.add(Predicates.not(CardType.LAND.getPredicate()));
        planeswalkerFilter.add(TargetController.YOU.getControllerPredicate());
        planeswalkerFilter.add(AnotherPredicate.instance);
    }

    public AjaniUnyielding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);

        this.setStartingLoyalty(4);

        // +2: Reveal the top three cards of your library. Put all nonland permanent cards revealed this way into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new LoyaltyAbility(new RevealLibraryPutIntoHandEffect(3, nonlandPermanentFilter, Zone.LIBRARY), 2));

        // -2: Exile target creature. Its controller gains life equal to its power.
        LoyaltyAbility ajaniAbility2 = new LoyaltyAbility(new ExileAndGainLifeEqualPowerTargetEffect(), -2);
        ajaniAbility2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ajaniAbility2);

        // -9: Put five +1/+1 counters on each creature you control and five loyalty counters on each other planeswalker you control.
        LoyaltyAbility ajaniAbility3 = new LoyaltyAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(5), new FilterControlledCreaturePermanent()), -9);
        ajaniAbility3.addEffect(new AddCountersAllEffect(CounterType.LOYALTY.createInstance(5), planeswalkerFilter).setText("and five loyalty counters on each other planeswalker you control"));
        this.addAbility(ajaniAbility3);
    }

    private AjaniUnyielding(final AjaniUnyielding card) {
        super(card);
    }

    @Override
    public AjaniUnyielding copy() {
        return new AjaniUnyielding(this);
    }
}
