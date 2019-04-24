package mage.cards.v;

import mage.abilities.LoyaltyAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.AssassinToken2;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VraskaSwarmsEminence extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature you control with deathtouch");

    static {
        filter.add(new AbilityPredicate(DeathtouchAbility.class));
    }

    public VraskaSwarmsEminence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{B/G}{B/G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VRASKA);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(5));

        // Whenever a creature you control with deathtouch deals damage to a player or planeswalker, put a +1/+1 counter on that creature.
        // TODO: make this trigger on planeswalkers
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new AddCountersTargetEffect(
                        CounterType.P1P1.createInstance()
                ).setText("put a +1/+1 counter on that creature"),
                filter, false, SetTargetPointer.PERMANENT, false)
        );

        // -2: Create a 1/1 black Assassin creature token with deathtouch and "Whenever this creature deals damage to a planeswalker, destroy that planeswalker."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new AssassinToken2()), -2));
    }

    private VraskaSwarmsEminence(final VraskaSwarmsEminence card) {
        super(card);
    }

    @Override
    public VraskaSwarmsEminence copy() {
        return new VraskaSwarmsEminence(this);
    }
}
