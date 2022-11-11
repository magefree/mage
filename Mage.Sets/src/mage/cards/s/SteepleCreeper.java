package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SteepleCreeper extends CardImpl {

    public SteepleCreeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // {3}{U}: Steeple Creeper gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new GainAbilitySourceEffect(
                        FlyingAbility.getInstance(), Duration.EndOfTurn
                ), new ManaCostsImpl<>("{3}{U}")
        ));
    }

    private SteepleCreeper(final SteepleCreeper card) {
        super(card);
    }

    @Override
    public SteepleCreeper copy() {
        return new SteepleCreeper(this);
    }
}
