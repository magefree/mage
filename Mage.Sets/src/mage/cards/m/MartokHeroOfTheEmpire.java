package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttackedThisTurnOpponentsCount;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetadjustment.TargetsCountAdjuster;
import mage.watchers.common.PlayersAttackedThisTurnWatcher;

/**
 * @author muz
 */
public final class MartokHeroOfTheEmpire extends CardImpl {

    public MartokHeroOfTheEmpire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Battlefront -- Whenever you attack, put a +1/+1 counter on each of up to X target creatures you control, where X is the number of opponents you attacked this turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setText("put a +1/+1 counter on each of up to X target creatures you control, "
                    + "where X is the number of opponents you attacked this turn"), 1
        ).withFlavorWord("Battlefront").addHint(AttackedThisTurnOpponentsCount.getHint());
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        ability.setTargetAdjuster(new TargetsCountAdjuster(AttackedThisTurnOpponentsCount.instance));
        this.addAbility(ability, new PlayersAttackedThisTurnWatcher());
    }

    private MartokHeroOfTheEmpire(final MartokHeroOfTheEmpire card) {
        super(card);
    }

    @Override
    public MartokHeroOfTheEmpire copy() {
        return new MartokHeroOfTheEmpire(this);
    }
}
