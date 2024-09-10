package mage.cards.c;

import java.util.UUID;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.AddCreatureTypeAdditionEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author Cguy7777
 */
public final class CallASurpriseWitness extends CardImpl {

    private static final FilterCreatureCard filter
            = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public CallASurpriseWitness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Return target creature card with mana value 3 or less from your graveyard to the battlefield.
        // Put a flying counter on it. It's a Spirit in addition to its other types.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.FLYING.createInstance())
                .setText("put a flying counter on it"));
        this.getSpellAbility().addEffect(new AddCreatureTypeAdditionEffect(SubType.SPIRIT, false)
                .setText("it's a Spirit in addition to its other types"));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
    }

    private CallASurpriseWitness(final CallASurpriseWitness card) {
        super(card);
    }

    @Override
    public CallASurpriseWitness copy() {
        return new CallASurpriseWitness(this);
    }
}
