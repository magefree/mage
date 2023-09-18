package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VivienNaturesAvenger extends CardImpl {

    public VivienNaturesAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VIVIEN);
        this.setStartingLoyalty(3);

        // +1: Put three +1/+1 counters on up to one target creature.
        Ability ability = new LoyaltyAbility(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance(3)
        ), 1);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // −1: Reveal cards from the top of your library until you reveal a creature card. Put that card into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new LoyaltyAbility(new RevealCardsFromLibraryUntilEffect(
                StaticFilters.FILTER_CARD_CREATURE, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ), -1));

        // −6: Target creature gets +10/+10 and gains trample until end of turn.
        ability = new LoyaltyAbility(
                new BoostTargetEffect(10, 10).setText("target creature gets +10/+10"), -6
        );
        ability.addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample until end of turn."));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private VivienNaturesAvenger(final VivienNaturesAvenger card) {
        super(card);
    }

    @Override
    public VivienNaturesAvenger copy() {
        return new VivienNaturesAvenger(this);
    }
}
