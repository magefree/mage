
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.DeathtouchRatToken;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Saga
 */
public final class HungryLynx extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filterCat = new FilterControlledCreaturePermanent("Cats");
    static {
        filterCat.add(SubType.CAT.getPredicate());
    }
    
    private static final FilterCard filterProRat = new FilterCard("Rats");
    static {
        filterProRat.add(SubType.RAT.getPredicate());
    }
    
    private static final FilterCreaturePermanent filterRat = new FilterCreaturePermanent("a Rat");
    static {
        filterRat.add(SubType.RAT.getPredicate());
    }
    
    public HungryLynx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Cats you control have protection from Rats.
        Effect effect = new GainAbilityAllEffect(new ProtectionAbility(filterProRat), Duration.WhileOnBattlefield, filterCat);
        effect.setText("Cats you control have protection from Rats. <i>(They can't be blocked, targeted, or dealt damage by Rats.)</i>");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        
        // At the beginning of your end step, target opponent creates a 1/1 black Rat creature token with deathtouch. 
        Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new CreateTokenTargetEffect(new DeathtouchRatToken()), TargetController.YOU, null, false);
        Target target = new TargetOpponent();
        ability.addTarget(target);
        this.addAbility(ability);
        
        // Whenever a Rat dies, put a +1/+1 counter on each Cat you control. 
        Effect effect2 = new AddCountersAllEffect(CounterType.P1P1.createInstance(), filterCat);
        effect2.setText("put a +1/+1 counter on each Cat you control");
        this.addAbility(new DiesCreatureTriggeredAbility(effect2, false, filterRat));
    }

    private HungryLynx(final HungryLynx card) {
        super(card);
    }

    @Override
    public HungryLynx copy() {
        return new HungryLynx(this);
    }
}
