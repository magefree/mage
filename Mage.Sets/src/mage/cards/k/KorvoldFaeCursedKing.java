package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KorvoldFaeCursedKing extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public KorvoldFaeCursedKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Korvold, Fae-Cursed King enters the battlefield or attacks, sacrifice another permanent.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new SacrificeControllerEffect(filter, 1, "")
        ));

        // Whenever you sacrifice a permanent, put a +1/+1 counter on Korvold and draw a card.
        Ability ability = new SacrificePermanentTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_PERMANENT);
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private KorvoldFaeCursedKing(final KorvoldFaeCursedKing card) {
        super(card);
    }

    @Override
    public KorvoldFaeCursedKing copy() {
        return new KorvoldFaeCursedKing(this);
    }
}
