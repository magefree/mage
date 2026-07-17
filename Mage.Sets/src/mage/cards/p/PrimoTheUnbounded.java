package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.BasePowerPredicate;
import mage.game.permanent.token.FractalToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class PrimoTheUnbounded extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creatures you control with base power 0");

    static {
        filter.add(new BasePowerPredicate(ComparisonType.EQUAL_TO, 0));
    }

    public PrimoTheUnbounded(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FRACTAL);
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Primo enters with twice X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
            new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance(), 2)
        ));

        // Whenever one or more creatures you control with base power 0 deal combat damage to a player,
        // create a 0/0 green and blue Fractal creature token. Put a number of +1/+1 counters on it equal to the damage dealt.
        this.addAbility(new OneOrMoreCombatDamagePlayerTriggeredAbility(
            FractalToken.getEffect(
                SavedDamageValue.MANY,
                ". Put a number of +1/+1 counters on it equal to the damage dealt"
            ),
            filter
        ));
    }

    private PrimoTheUnbounded(final PrimoTheUnbounded card) {
        super(card);
    }

    @Override
    public PrimoTheUnbounded copy() {
        return new PrimoTheUnbounded(this);
    }
}
