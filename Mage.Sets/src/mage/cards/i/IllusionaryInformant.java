package mage.cards.i;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author tiera3 - based on ChardalynDragon
 * note - draftmatters ability not implemented
 */
public final class IllusionaryInformant extends CardImpl {

    public IllusionaryInformant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private IllusionaryInformant(final IllusionaryInformant card) {
        super(card);
    }

    @Override
    public IllusionaryInformant copy() {
        return new IllusionaryInformant(this);
    }
}
