package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;
import mage.target.targetpointer.ThirdTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EverythingPizza extends CardImpl {

    public EverythingPizza(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.FOOD);

        // When this artifact enters, search your library for a basic land card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(1, 1, StaticFilters.FILTER_CARD_BASIC_LAND), true
        )));

        // {2}{W}{U}{B}{R}{G}, {T}, Sacrifice this artifact: Target player gains 3 life and draws a card. Each of your opponents discards a card. This artifact deals 3 damage to any target. Put three +1/+1 counters on up to one target creature.
        Ability ability = new SimpleActivatedAbility(new GainLifeTargetEffect(3), new ManaCostsImpl<>("{2}{W}{U}{B}{R}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new DrawCardTargetEffect(1).withTargetDescription("and"));
        ability.addTarget(new TargetPlayer());
        ability.addEffect(new DiscardEachPlayerEffect(TargetController.OPPONENT).setText("each of your opponents discards a card"));
        ability.addEffect(new DamageTargetEffect(3).setTargetPointer(new SecondTargetPointer()));
        ability.addTarget(new TargetAnyTarget());
        ability.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(3)).setTargetPointer(new ThirdTargetPointer()));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private EverythingPizza(final EverythingPizza card) {
        super(card);
    }

    @Override
    public EverythingPizza copy() {
        return new EverythingPizza(this);
    }
}
