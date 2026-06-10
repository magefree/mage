package mage.cards.v;

import java.util.Optional;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class VivVisionTeenSynthezoid extends CardImpl {

    public VivVisionTeenSynthezoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Cybernetic Senses -- Whenever Viv Vision attacks, draw a card if her power is 4 or greater.
       this.addAbility(new AttacksTriggeredAbility(
            new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                VivVisionTeenSynthezoidCondition.instance,
                "draw a card if her power is 4 or greater"
            )
        ).withFlavorWord("Cybernetic Senses"));

        // Power-up -- {7}: Put two +1/+1 counters on Viv Vision.
        this.addAbility(new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
            new ManaCostsImpl<>("{7}")
        ));

    }

    private VivVisionTeenSynthezoid(final VivVisionTeenSynthezoid card) {
        super(card);
    }

    @Override
    public VivVisionTeenSynthezoid copy() {
        return new VivVisionTeenSynthezoid(this);
    }
}

enum VivVisionTeenSynthezoidCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
            .ofNullable(source.getSourcePermanentOrLKI(game))
            .map(MageObject::getPower)
            .map(MageInt::getValue)
            .orElse(0) >= 4;
    }

    @Override
    public String toString() {
        return "her power is 4 or greater";
    }
}
