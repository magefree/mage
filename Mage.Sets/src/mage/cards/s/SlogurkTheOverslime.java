package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class SlogurkTheOverslime extends CardImpl {

    public SlogurkTheOverslime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a land card is put into your graveyard from anywhere, put a +1/+1 counter on Slogurk, the Overslime.
        this.addAbility(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                false, StaticFilters.FILTER_CARD_LAND_A, TargetController.YOU
        ));

        // Remove three +1/+1 counters from Slogurk: Return it to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(
                new ReturnToHandSourceEffect().setText("return it to its owner's hand"),
                new RemoveCountersSourceCost(CounterType.P1P1.createInstance(3))
        ));

        // When Slogurk leaves the battlefield, return up to three target land cards from your graveyard to your hand.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new ReturnToHandTargetEffect()
                .setText("return up to three target land cards from your graveyard to your hand"), false);
        ability.addTarget(new TargetCardInYourGraveyard(
                0, 3, new FilterLandCard("land cards from your graveyard")));
        this.addAbility(ability);
    }

    private SlogurkTheOverslime(final SlogurkTheOverslime card) {
        super(card);
    }

    @Override
    public SlogurkTheOverslime copy() {
        return new SlogurkTheOverslime(this);
    }
}
