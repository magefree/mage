
package mage.cards.n;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.PlantToken;

/**
 *
 * @author fireshoes
 */
public final class NissaVoiceOfZendikar extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("lands you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public NissaVoiceOfZendikar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{1}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NISSA);

        this.setStartingLoyalty(3);

        // +1: Create a 0/1 green Plant creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new PlantToken()), 1));

        // -2: Put a +1/+1 counter on each creature you control.
        this.addAbility(new LoyaltyAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), new FilterControlledCreaturePermanent()), -2));

        // -7: You gain X life and draw X cards, where X is the number of lands you control.
        Effect effect = new GainLifeEffect(new PermanentsOnBattlefieldCount(filter));
        effect.setText("you gain X life");
        LoyaltyAbility ability = new LoyaltyAbility(effect, -7);
        effect = new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter));
        effect.setText("and draw X cards, where X is the number of lands you control");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private NissaVoiceOfZendikar(final NissaVoiceOfZendikar card) {
        super(card);
    }

    @Override
    public NissaVoiceOfZendikar copy() {
        return new NissaVoiceOfZendikar(this);
    }
}
