package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.CatBirdToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkycatSovereign extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("other creature you control with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public SkycatSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Skycat Sovereign gets +1/+1 for each other creature you control with flying.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield)));

        // {2}{W}{U}: Create a 1/1 white Cat Bird creature token with flying.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new CatBirdToken()), new ManaCostsImpl<>("{2}{W}{U}")
        ));
    }

    private SkycatSovereign(final SkycatSovereign card) {
        super(card);
    }

    @Override
    public SkycatSovereign copy() {
        return new SkycatSovereign(this);
    }
}
