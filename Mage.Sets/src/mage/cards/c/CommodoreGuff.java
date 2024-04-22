package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.CommodoreGuffToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CommodoreGuff extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPlaneswalkerPermanent("another target planeswalker you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER);
    private static final Hint hint = new ValueHint("Planeswalkers you control", xValue);

    public CommodoreGuff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GUFF);
        this.setStartingLoyalty(5);

        // At the beginning of your end step, put a loyalty counter on another target planeswalker you control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersTargetEffect(CounterType.LOYALTY.createInstance()), TargetController.YOU, false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // +1: Create a 1/1 red Wizard creature token with "{T}: Add {R}. Spend this mana only to cast a planeswalker spell."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new CommodoreGuffToken()), 1));

        // -3: You draw X cards and Commodore Guff deals X damage to each opponent, where X is the number of planeswalkers you control.
        ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(xValue).setText("you draw X cards"), -3);
        ability.addEffect(new DamagePlayersEffect(
                Outcome.Damage, xValue, TargetController.OPPONENT
        ).concatBy("and"));
        this.addAbility(ability.addHint(hint));

        // Commodore Guff can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private CommodoreGuff(final CommodoreGuff card) {
        super(card);
    }

    @Override
    public CommodoreGuff copy() {
        return new CommodoreGuff(this);
    }
}
