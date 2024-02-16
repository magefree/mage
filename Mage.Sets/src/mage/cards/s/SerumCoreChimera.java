package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

public class SerumCoreChimera extends CardImpl {
    public SerumCoreChimera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");
        this.addSubType(SubType.PHYREXIAN);
        this.addSubType(SubType.CHIMERA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        //Flying
        this.addAbility(FlyingAbility.getInstance());

        //Whenever you cast a noncreature spell, put an oil counter on Serum-Core Chimera.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.OIL.createInstance()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false));

        //Remove three oil counters from Serum-Core Chimera: Draw a card. Then you may discard a nonland card. When you
        //discard a card this way, Serum-Core Chimera deals 3 damage to target creature or planeswalker. Activate only
        //as a sorcery.
        ActivateAsSorceryActivatedAbility activateAsSorceryActivatedAbility = new ActivateAsSorceryActivatedAbility(
                new DrawCardSourceControllerEffect(1).setText("Draw a card"),
                new RemoveCountersSourceCost(CounterType.OIL.createInstance(3)));
        ReflexiveTriggeredAbility reflexiveTriggeredAbility = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(3), false
        );
        reflexiveTriggeredAbility.addTarget(new TargetCreatureOrPlaneswalker());
        activateAsSorceryActivatedAbility.addEffect(new DoWhenCostPaid(reflexiveTriggeredAbility,
                new DiscardCardCost(StaticFilters.FILTER_CARD_A_NON_LAND), "Discard nonland card?")
                .setText("Then you may discard a nonland card. When you discard a card this way, Serum-Core Chimera " +
                "deals 3 damage to target creature or planeswalker."));
        this.addAbility(activateAsSorceryActivatedAbility);
    }

    private SerumCoreChimera(final SerumCoreChimera card) {
        super(card);
    }

    @Override
    public SerumCoreChimera copy() {
        return new SerumCoreChimera(this);
    }
}
