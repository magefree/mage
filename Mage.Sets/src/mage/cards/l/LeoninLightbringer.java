package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeoninLightbringer extends CardImpl {

    public LeoninLightbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // As long as Leonin Lightbringer is equipped, it gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                EquippedSourceCondition.instance, "as long as {this} is equipped, it gets +1/+1"
        )));
    }

    private LeoninLightbringer(final LeoninLightbringer card) {
        super(card);
    }

    @Override
    public LeoninLightbringer copy() {
        return new LeoninLightbringer(this);
    }
}
