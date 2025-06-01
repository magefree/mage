
package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HoodedAssassin extends CardImpl {

    public HoodedAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Hooded Assassin enters the battlefield, choose one -
        // * Put a +1/+1 counter on Hooded Assassin.        
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
        // * Destroy target creature that was dealt damage this turn.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_DAMAGED_THIS_TURN));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private HoodedAssassin(final HoodedAssassin card) {
        super(card);
    }

    @Override
    public HoodedAssassin copy() {
        return new HoodedAssassin(this);
    }
}
