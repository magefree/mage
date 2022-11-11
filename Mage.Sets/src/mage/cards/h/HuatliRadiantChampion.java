package mage.cards.h;

import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.command.emblems.HuatliRadiantChampionEmblem;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HuatliRadiantChampion extends CardImpl {

    public HuatliRadiantChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUATLI);
        this.setStartingLoyalty(3);

        // +1: Put a loyalty counter on Huatli, Radiant Champion for each creature you control.
        this.addAbility(new LoyaltyAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(0),
                new PermanentsOnBattlefieldCount(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED), true), 1));

        // -1: Target creature gets +X/+X until end of turn, where X is the number of creatures you control.
        PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED);
        LoyaltyAbility ability2 = new LoyaltyAbility(new BoostTargetEffect(amount, amount, Duration.EndOfTurn)
                .setText("Target creature gets +X/+X until end of turn, where X is the number of creatures you control"), -1);
        ability2.addTarget(new TargetCreaturePermanent());
        ability2.addHint(CreaturesYouControlHint.instance);
        this.addAbility(ability2);

        // -8: You get an emblem with "Whenever a creature enters the battlefield under your control, you may draw a card."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new HuatliRadiantChampionEmblem()), -8));
    }

    private HuatliRadiantChampion(final HuatliRadiantChampion card) {
        super(card);
    }

    @Override
    public HuatliRadiantChampion copy() {
        return new HuatliRadiantChampion(this);
    }
}
