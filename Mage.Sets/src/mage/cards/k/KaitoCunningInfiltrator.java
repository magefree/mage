package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.command.emblems.KaitoCunningInfiltratorEmblem;
import mage.game.permanent.token.NinjaToken2;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class KaitoCunningInfiltrator extends CardImpl {

    public KaitoCunningInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KAITO);
        this.setStartingLoyalty(3);

        // Whenever a creature you control deals combat damage to a player, put a loyalty counter on Kaito.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.LOYALTY.createInstance()),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                false, SetTargetPointer.NONE, true
        ));

        // +1: Up to one target creature you control can't be blocked this turn. Draw a card, then discard a card.
        Ability plusOneAbility = new LoyaltyAbility(new CantBeBlockedTargetEffect(Duration.EndOfTurn), 1);
        plusOneAbility.addEffect(new DrawDiscardControllerEffect());
        plusOneAbility.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(plusOneAbility);

        // -2: Create a 2/1 blue Ninja creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new NinjaToken2()), -2));

        // -9: You get an emblem with "Whenever a player casts a spell, you create a 2/1 blue Ninja creature token."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new KaitoCunningInfiltratorEmblem()), -9));
    }

    private KaitoCunningInfiltrator(final KaitoCunningInfiltrator card) {
        super(card);
    }

    @Override
    public KaitoCunningInfiltrator copy() {
        return new KaitoCunningInfiltrator(this);
    }
}
