
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.watchers.common.CastFromHandWatcher;

/**
 * @author LevelX
 */
public final class MyojinOfSeeingWinds extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent you control");
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public MyojinOfSeeingWinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}{U}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.getSpellAbility().addWatcher(new CastFromHandWatcher());

        // Myojin of Seeing Winds enters the battlefield with a divinity counter on it if you cast it from your hand.
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(new AddCountersSourceEffect(CounterType.DIVINITY.createInstance()), CastFromHandSourcePermanentCondition.instance, ""), "with a divinity counter on it if you cast it from your hand"));
        // Myojin of Seeing Winds has indestructible as long as it has a divinity counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                new SourceHasCounterCondition(CounterType.DIVINITY), "{this} has indestructible as long as it has a divinity counter on it")));
        // Remove a divinity counter from Myojin of Seeing Winds: Draw a card for each permanent you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter)), new RemoveCountersSourceCost(CounterType.DIVINITY.createInstance()));
        this.addAbility(ability);
    }

    private MyojinOfSeeingWinds(final MyojinOfSeeingWinds card) {
        super(card);
    }

    @Override
    public MyojinOfSeeingWinds copy() {
        return new MyojinOfSeeingWinds(this);
    }
}
