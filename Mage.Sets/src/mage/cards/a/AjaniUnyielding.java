
package mage.cards.a;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
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
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author JRHerlehy
 */
public final class AjaniUnyielding extends CardImpl {

    private static final FilterPermanentCard nonlandPermanentFilter = new FilterPermanentCard("nonland permanent cards");
    private static final FilterPlaneswalkerPermanent planeswalkerFilter = new FilterPlaneswalkerPermanent("other planeswalker you control");

    static {
        nonlandPermanentFilter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
        planeswalkerFilter.add(new ControllerPredicate(TargetController.YOU));
        planeswalkerFilter.add(new AnotherPredicate());
    }

    public AjaniUnyielding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{G}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);

        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // +2: Reveal the top three cards of your library. Put all nonland permanent cards revealed this way into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new LoyaltyAbility(new RevealLibraryPutIntoHandEffect(3, nonlandPermanentFilter, Zone.LIBRARY), 2));

        // -2: Exile target creature. Its controller gains life equal to its power.
        LoyaltyAbility ajaniAbility2 = new LoyaltyAbility(new ExileAndGainLifeEqualPowerTargetEffect(), -2);
        ajaniAbility2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ajaniAbility2);

        // -9: Put five +1/+1 counters on each creature you control and five loyalty counters on each other planeswalker you control.
        LoyaltyAbility ajaniAbility3 = new LoyaltyAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(5), new FilterControlledCreaturePermanent()), -9);
        ajaniAbility3.addEffect(new AddCountersAllEffect(CounterType.LOYALTY.createInstance(5), planeswalkerFilter));
        this.addAbility(ajaniAbility3);
    }

    public AjaniUnyielding(final AjaniUnyielding card) {
        super(card);
    }

    @Override
    public AjaniUnyielding copy() {
        return new AjaniUnyielding(this);
    }
}
