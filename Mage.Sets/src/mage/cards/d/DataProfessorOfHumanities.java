package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.FaerieToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class DataProfessorOfHumanities extends CardImpl {

    public DataProfessorOfHumanities(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANDROID);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Seek out -- Whenever you draw your second card each turn, create two 1/1 blue Faerie creature tokens with flying.
        this.addAbility(new DrawNthCardTriggeredAbility(new CreateTokenEffect(new FaerieToken(), 2))
            .withFlavorWord("Seek out"));
    }

    private DataProfessorOfHumanities(final DataProfessorOfHumanities card) {
        super(card);
    }

    @Override
    public DataProfessorOfHumanities copy() {
        return new DataProfessorOfHumanities(this);
    }
}
