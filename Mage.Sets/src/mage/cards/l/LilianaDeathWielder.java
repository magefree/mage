package mage.cards.l;

import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class LilianaDeathWielder extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with a -1/-1 counter on it");

    static {
        filter.add(CounterType.M1M1.getPredicate());
    }

    public LilianaDeathWielder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{5}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LILIANA);

        this.setStartingLoyalty(5);

        // +2: Put a -1/-1 counter on up to one target creature.
        LoyaltyAbility ability = new LoyaltyAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance(1)), 2);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // -3: Destroy target creature with a -1/-1 counter on it.
        ability = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // -10: Return all creature cards from your graveyard to the battlefield.
        this.addAbility(new LoyaltyAbility(new ReturnFromYourGraveyardToBattlefieldAllEffect(StaticFilters.FILTER_CARD_CREATURES), -10));
    }

    private LilianaDeathWielder(final LilianaDeathWielder card) {
        super(card);
    }

    @Override
    public LilianaDeathWielder copy() {
        return new LilianaDeathWielder(this);
    }
}
