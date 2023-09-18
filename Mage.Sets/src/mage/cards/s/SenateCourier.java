package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SenateCourier extends CardImpl {

    public SenateCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{W}: Senate Courier gains vigilance until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{1}{W}")));
    }

    private SenateCourier(final SenateCourier card) {
        super(card);
    }

    @Override
    public SenateCourier copy() {
        return new SenateCourier(this);
    }
}
