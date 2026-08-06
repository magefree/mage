package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

/**
 *
 * @author muz
 */
public final class GandalfShadowsFoe extends CardImpl {

    public GandalfShadowsFoe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Gandalf enters, exile up to three target lands you control, then return them to the battlefield tapped under their owner's control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileThenReturnTargetEffect(false, false, PutCards.BATTLEFIELD_TAPPED));
        ability.addTarget(new TargetPermanent(0, 3, StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS));
        this.addAbility(ability);

        // Landfall -- Whenever a land you control enters, draw a card and put a +1/+1 counter on Gandalf.
        Ability landfallAbility = new LandfallAbility(new DrawCardSourceControllerEffect(1), false);
        landfallAbility.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy("and"));
        this.addAbility(landfallAbility);
    }

    private GandalfShadowsFoe(final GandalfShadowsFoe card) {
        super(card);
    }

    @Override
    public GandalfShadowsFoe copy() {
        return new GandalfShadowsFoe(this);
    }
}
