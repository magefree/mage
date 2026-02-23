package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireNationSalvagers extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creatures you control with counters on them");
    private static final FilterCard filter2 = new FilterCard("creature or Vehicle card from that player's graveyard");

    static {
        filter.add(CounterAnyPredicate.instance);
        filter2.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public FireNationSalvagers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // When this creature enters, put a +1/+1 counter on target creature or Vehicle you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_CREATURE_OR_VEHICLE));
        this.addAbility(ability);

        // Whenever one or more creatures you control with counters on them deal combat damage to a player, put target creature or Vehicle card from that player's graveyard onto the battlefield under your control.
        ability = new OneOrMoreCombatDamagePlayerTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), SetTargetPointer.PLAYER, filter, false
        );
        ability.addTarget(new TargetCardInGraveyard(filter2));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster(true));
        this.addAbility(ability);
    }

    private FireNationSalvagers(final FireNationSalvagers card) {
        super(card);
    }

    @Override
    public FireNationSalvagers copy() {
        return new FireNationSalvagers(this);
    }
}
