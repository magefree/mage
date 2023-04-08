package mage.cards.f;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FurnaceGremlin extends CardImpl {

    public FurnaceGremlin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.GREMLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {1}{R}: Furnace Gremlin gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}")
        ));

        // When Furnace Gremlin dies, incubate X, where X is Furnace Gremlin's power.
        this.addAbility(new DiesSourceTriggeredAbility(new FurnaceGremlinEffect()));
    }

    private FurnaceGremlin(final FurnaceGremlin card) {
        super(card);
    }

    @Override
    public FurnaceGremlin copy() {
        return new FurnaceGremlin(this);
    }
}

class FurnaceGremlinEffect extends OneShotEffect {

    FurnaceGremlinEffect() {
        super(Outcome.Benefit);
        staticText = "incubate X, where X is {this}'s power";
    }

    private FurnaceGremlinEffect(final FurnaceGremlinEffect effect) {
        super(effect);
    }

    @Override
    public FurnaceGremlinEffect copy() {
        return new FurnaceGremlinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int power = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0);
        return new IncubateEffect(power).apply(game, source);
    }
}
