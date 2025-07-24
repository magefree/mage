package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiderHamPeterPorker extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(
            "Spiders, Boars, Bats, Bears, Birds, Cats, Dogs, Frogs, Jackals, Lizards, " +
                    "Mice, Otters, Rabbits, Raccoons, Rats, Squirrels, Turtles, and Wolves"
    );

    static {
        filter.add(Predicates.or(
                SubType.SPIDER.getPredicate(),
                SubType.BOAR.getPredicate(),
                SubType.BAT.getPredicate(),
                SubType.BEAR.getPredicate(),
                SubType.BIRD.getPredicate(),
                SubType.CAT.getPredicate(),
                SubType.DOG.getPredicate(),
                SubType.FROG.getPredicate(),
                SubType.JACKAL.getPredicate(),
                SubType.LIZARD.getPredicate(),
                SubType.MOUSE.getPredicate(),
                SubType.OTTER.getPredicate(),
                SubType.RABBIT.getPredicate(),
                SubType.RACCOON.getPredicate(),
                SubType.RAT.getPredicate(),
                SubType.SQUIRREL.getPredicate(),
                SubType.TURTLE.getPredicate(),
                SubType.WOLF.getPredicate()
        ));
    }

    public SpiderHamPeterPorker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Spider-Ham enters, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // Animal May-Ham -- Other Spiders, Boars, Bats, Bears, Birds, Cats, Dogs, Frogs, Jackals, Lizards, Mice, Otters, Rabbits, Raccoons, Rats, Squirrels, Turtles, and Wolves you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )).withFlavorWord("Animal May-Ham"));
    }

    private SpiderHamPeterPorker(final SpiderHamPeterPorker card) {
        super(card);
    }

    @Override
    public SpiderHamPeterPorker copy() {
        return new SpiderHamPeterPorker(this);
    }
}
// it CAN get weirder!
