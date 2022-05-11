package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author North
 */
public final class ImmolatingSouleater extends CardImpl {

    public ImmolatingSouleater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                1, 0, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{R/P}")));
    }

    private ImmolatingSouleater(final ImmolatingSouleater card) {
        super(card);
    }

    @Override
    public ImmolatingSouleater copy() {
        return new ImmolatingSouleater(this);
    }
}
