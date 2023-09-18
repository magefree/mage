
package mage.cards.a;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.command.emblems.AjaniSteadfastEmblem;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AjaniSteadfast extends CardImpl {

    private static final FilterPlaneswalkerPermanent filter = new FilterPlaneswalkerPermanent("other planeswalker you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public AjaniSteadfast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);

        this.setStartingLoyalty(4);

        // +1: Until end of turn, up to one target creature gets +1/+1 and gains first strike, vigilance, and lifelink.
        Effect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
        effect.setText("Until end of turn, up to one target creature gets +1/+1");
        LoyaltyAbility ability = new LoyaltyAbility(effect, 1);
        effect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains first strike");
        ability.addEffect(effect);
        effect = new GainAbilityTargetEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn);
        effect.setText(", vigilance");
        ability.addEffect(effect);
        effect = new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        effect.setText(", and lifelink");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // -2: Put a +1/+1 counter on each creature you control and a loyalty counter on each other planeswalker you control.
        ability = new LoyaltyAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), new FilterControlledCreaturePermanent()), -2);
        effect = new AddCountersAllEffect(CounterType.LOYALTY.createInstance(), filter);
        effect.setText("and a loyalty counter on each other planeswalker you control");
        ability.addEffect(effect);
        this.addAbility(ability);

        // -7: You get an emblem with "If a source would deal damage to you or a planeswalker you control, prevent all but 1 of that damage."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new AjaniSteadfastEmblem()), -7));
    }

    private AjaniSteadfast(final AjaniSteadfast card) {
        super(card);
    }

    @Override
    public AjaniSteadfast copy() {
        return new AjaniSteadfast(this);
    }
}
