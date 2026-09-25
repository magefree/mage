package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.OneShotNonTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterSuspendedCard;
import mage.target.common.TargetCardInExile;

import java.util.UUID;

/**
 *
 * @author Skiwkr
 */
public final class AmyPond extends CardImpl {

    private static final FilterSuspendedCard filter = new FilterSuspendedCard("suspended card you own");
    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public AmyPond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Partner with Rory Williams
        this.addAbility(new PartnerWithAbility("Rory Williams"));

        // Whenever Amy Pond deals combat damage to a player, choose a suspended card you own and remove that many time counters from it.
        OneShotEffect removeTimeCounters = new RemoveCounterTargetEffect(CounterType.TIME.createInstance(), SavedDamageValue.MANY)
                .setText("choose a suspended card you own and remove that many time counters from it");
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new OneShotNonTargetEffect(removeTimeCounters, new TargetCardInExile(filter))));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());

    }

    private AmyPond(final AmyPond card) {
        super(card);
    }

    @Override
    public AmyPond copy() {
        return new AmyPond(this);
    }
}
