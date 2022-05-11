package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DaringArchaeologist extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a historic spell");

    static {
        filter.add(HistoricPredicate.instance);
    }

    public DaringArchaeologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Daring Archaeologist enters the battlefield, you may return target artifact card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect()
                .setText("you may return target artifact card from your graveyard to your hand"), true);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // Whenever you cast a historic spell, put a +1/+1 counter on Daring Archaeologist.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1))
                        .setText("put a +1/+1 counter on {this}. <i>(Artifacts, legendaries, and Sagas are historic.)</i>"),
                filter, false));
    }

    private DaringArchaeologist(final DaringArchaeologist card) {
        super(card);
    }

    @Override
    public DaringArchaeologist copy() {
        return new DaringArchaeologist(this);
    }
}
