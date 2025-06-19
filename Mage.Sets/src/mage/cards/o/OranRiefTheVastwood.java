package mage.cards.o;

import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class OranRiefTheVastwood extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("green creature that entered this turn");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(EnteredThisTurnPredicate.instance);
    }

    public OranRiefTheVastwood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new GreenManaAbility());
        this.addAbility(new SimpleActivatedAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), new TapSourceCost()));
    }

    private OranRiefTheVastwood(final OranRiefTheVastwood card) {
        super(card);
    }

    @Override
    public OranRiefTheVastwood copy() {
        return new OranRiefTheVastwood(this);
    }
}
