
package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SpiritWhiteToken;

/**
 *
 * @author LevelX2
 */
public final class AbzanAscendancy extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.not(TokenPredicate.instance));
    }

    public AbzanAscendancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{B}{G}");

        // When Abzan Ascendancy enters the battlefield, put a +1/+1 counter on each creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE), false));

        // Whenever a nontoken creature you control dies, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken("KTK")), false, filter));

    }

    public AbzanAscendancy(final AbzanAscendancy card) {
        super(card);
    }

    @Override
    public AbzanAscendancy copy() {
        return new AbzanAscendancy(this);
    }
}
