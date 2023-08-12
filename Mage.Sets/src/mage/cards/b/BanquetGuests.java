package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BanquetGuests extends CardImpl {

    private static final Hint hint = new ValueHint("Food you control", new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_FOOD));

    public BanquetGuests(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{W}");

        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Affinity for Food
        this.addAbility(
                new SimpleStaticAbility(
                        Zone.ALL,
                        new AffinityEffect(StaticFilters.FILTER_CONTROLLED_FOOD)
                ).addHint(hint)
        );

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Banquet Guests enters the battlefield with twice X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance(), 2)
        ));

        // {2}, Sacrifice a Food: Banquet Guests gains indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                new GenericManaCost(2)
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_FOOD)));
        this.addAbility(ability);
    }

    private BanquetGuests(final BanquetGuests card) {
        super(card);
    }

    @Override
    public BanquetGuests copy() {
        return new BanquetGuests(this);
    }
}
