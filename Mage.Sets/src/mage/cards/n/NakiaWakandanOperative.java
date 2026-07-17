package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class NakiaWakandanOperative extends CardImpl {

  private static final FilterPermanent filter = new FilterPermanent("your commander");

    static {
        filter.add(CommanderPredicate.instance);
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public NakiaWakandanOperative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever your commander enters, you become the monarch.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
            new BecomesMonarchSourceEffect(), filter
        ).addHint(MonarchHint.instance));

        // {2}, {T}: Put two +1/+1 counters on target creature or Vehicle. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)),
            new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_VEHICLE));
        this.addAbility(ability);
    }

    private NakiaWakandanOperative(final NakiaWakandanOperative card) {
        super(card);
    }

    @Override
    public NakiaWakandanOperative copy() {
        return new NakiaWakandanOperative(this);
    }
}
