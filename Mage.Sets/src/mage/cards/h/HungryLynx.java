package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.DeathtouchRatToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author Saga
 */
public final class HungryLynx extends CardImpl {
    
    private static final FilterControlledPermanent filterCat = new FilterControlledPermanent(SubType.CAT, "Cats");
    
    private static final FilterCard filterProRat = new FilterCard("Rats");
    static {
        filterProRat.add(SubType.RAT.getPredicate());
    }
    
    private static final FilterPermanent filterRat = new FilterPermanent(SubType.RAT, "a Rat");
    
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
        ability.addTarget(new TargetOpponent());
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
