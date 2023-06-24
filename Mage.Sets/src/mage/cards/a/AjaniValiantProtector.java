package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
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
 * @author Styxo
 */
public final class AjaniValiantProtector extends CardImpl {

    public AjaniValiantProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);

        this.setStartingLoyalty(4);

        // +2: Put two +1/+1 counters on up to one target creature.
        Ability ability = new LoyaltyAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)), 2);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // +1: Reveal cards from the top of your library until you reveal a creature card. Put that card into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new LoyaltyAbility(new RevealCardsFromLibraryUntilEffect(StaticFilters.FILTER_CARD_CREATURE, PutCards.HAND, PutCards.BOTTOM_RANDOM), 1));

        // -11: Put X +1/+1 counters on target creature, where X is your life total. That creature gains trample until end of turn.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(), ControllerLifeCount.instance);
        effect.setText("Put X +1/+1 counters on target creature, where X is your life total");
        ability = new LoyaltyAbility(effect, -11);
        effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("That creature gains trample until end of turn");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private AjaniValiantProtector(final AjaniValiantProtector card) {
        super(card);
    }

    @Override
    public AjaniValiantProtector copy() {
        return new AjaniValiantProtector(this);
    }
}
