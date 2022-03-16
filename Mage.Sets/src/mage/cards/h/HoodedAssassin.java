
package mage.cards.h;

import java.util.UUID;
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
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.WasDealtDamageThisTurnPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class HoodedAssassin extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that was dealt damage this turn");

    static {
        filter.add(WasDealtDamageThisTurnPredicate.instance);
    }
    
    public HoodedAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Hooded Assassin enters the battlefield, choose one -
        // * Put a +1/+1 counter on Hooded Assassin.        
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
        // * Destroy target creature that was dealt damage this turn.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetCreaturePermanent(filter));
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
