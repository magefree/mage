package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ChangelingAbility;
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
public final class Mistwalker extends CardImpl {

    public Mistwalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{U}: Mistwalker gets +1/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, -1, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{U}")
        ));
    }

    private Mistwalker(final Mistwalker card) {
        super(card);
    }

    @Override
    public Mistwalker copy() {
        return new Mistwalker(this);
    }
}
