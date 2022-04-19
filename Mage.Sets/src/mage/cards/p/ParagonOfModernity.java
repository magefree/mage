package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ParagonOfModernity extends CardImpl {

    public ParagonOfModernity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {3}: Paragon of Modernity gets +1/+1 until end of turn. If exactly three colors of mana were spent to activate this ability, put a +1/+1 counter on it instead.
        this.addAbility(new SimpleActivatedAbility(new ConditionalOneShotEffect(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new AddContinuousEffectToGame(new BoostSourceEffect(1, 1, Duration.EndOfTurn)),
                ParagonOfModernityCondition.instance, "{this} gets +1/+1 until end of turn. If exactly three " +
                "colors of mana were spent to activate this ability, put a +1/+1 counter on it instead"
        ), new GenericManaCost(3)));
    }

    private ParagonOfModernity(final ParagonOfModernity card) {
        super(card);
    }

    @Override
    public ParagonOfModernity copy() {
        return new ParagonOfModernity(this);
    }
}

enum ParagonOfModernityCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return source.getManaCosts().getUsedManaToPay().getDifferentColors() == 3;
    }
}
