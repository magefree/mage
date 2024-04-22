package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetPermanentAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JadeSeedstones extends CardImpl {

    public JadeSeedstones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{G}");
        this.secondSideCardClazz = mage.cards.j.JadeheartAttendant.class;

        // When Jade Seedstones enters the battlefield, distribute three +1/+1 counters among one, two, or three target creatures you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DistributeCountersEffect(
                CounterType.P1P1, 3, "one, two, or three target creatures you control"
        ));
        TargetPermanentAmount target = new TargetPermanentAmount(3, StaticFilters.FILTER_CONTROLLED_CREATURES);
        target.setMinNumberOfTargets(1);
        ability.addTarget(target);
        this.addAbility(ability);

        // Craft with creature {5}{G}{G}
        this.addAbility(new CraftAbility(
                "{5}{G}{G}", "creature", "another creature you control " +
                "or a creature card in your graveyard", CardType.CREATURE.getPredicate())
        );
    }

    private JadeSeedstones(final JadeSeedstones card) {
        super(card);
    }

    @Override
    public JadeSeedstones copy() {
        return new JadeSeedstones(this);
    }
}
