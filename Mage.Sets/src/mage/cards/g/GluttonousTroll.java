package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GluttonousTroll extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("another nonland permanent");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(AnotherPredicate.instance);
    }

    public GluttonousTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.subtype.add(SubType.TROLL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Gluttonous Troll enters the battlefield, create a number of Food tokens equal to the number of opponents you have.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(
                new FoodToken(), OpponentsCount.instance
        ).setText("create a number of Food tokens equal to the number of opponents you have")));

        // {1}{G}, Sacrifice another nonland permanent: Gluttonous Troll gets +2/+2 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostSourceEffect(2, 2, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{G}")
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private GluttonousTroll(final GluttonousTroll card) {
        super(card);
    }

    @Override
    public GluttonousTroll copy() {
        return new GluttonousTroll(this);
    }
}
