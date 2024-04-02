package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.SaddleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.SaddledSourceThisTurnPredicate;
import mage.target.TargetPermanent;
import mage.watchers.common.SaddledMountWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GiantBeaver extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature that saddled it this turn");

    static {
        filter.add(SaddledSourceThisTurnPredicate.instance);
    }

    public GiantBeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.BEAVER);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Giant Beaver attacks while saddled, put a +1/+1 counter on target creature that saddled it this turn.
        Ability ability = new AttacksWhileSaddledTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability, new SaddledMountWatcher());

        // Saddle 3
        this.addAbility(new SaddleAbility(3));
    }

    private GiantBeaver(final GiantBeaver card) {
        super(card);
    }

    @Override
    public GiantBeaver copy() {
        return new GiantBeaver(this);
    }
}
