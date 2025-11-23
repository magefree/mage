package mage.cards.t;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheArtOfTea extends CardImpl {

    public TheArtOfTea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        this.subtype.add(SubType.LESSON);

        // Put a +1/+1 counter on up to one target creature you control. Create a Food token.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new FoodToken()));
    }

    private TheArtOfTea(final TheArtOfTea card) {
        super(card);
    }

    @Override
    public TheArtOfTea copy() {
        return new TheArtOfTea(this);
    }
}
