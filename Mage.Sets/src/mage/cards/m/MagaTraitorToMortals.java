
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class MagaTraitorToMortals extends CardImpl {

    public MagaTraitorToMortals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{X}{B}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Maga, Traitor to Mortals enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // When Maga enters the battlefield, target player loses life equal to the number of +1/+1 counters on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new LoseLifeTargetEffect(new CountersSourceCount(CounterType.P1P1)), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private MagaTraitorToMortals(final MagaTraitorToMortals card) {
        super(card);
    }

    @Override
    public MagaTraitorToMortals copy() {
        return new MagaTraitorToMortals(this);
    }
}
