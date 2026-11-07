package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.NewPlanetToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class DavidAndCarolMarcusCreators extends CardImpl {

    public DavidAndCarolMarcusCreators(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When David and Carol Marcus enter, create a tapped Planet land token named New Planet with "{T}: Add one mana of any color."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new NewPlanetToken(), 1, true)));
    }

    private DavidAndCarolMarcusCreators(final DavidAndCarolMarcusCreators card) {
        super(card);
    }

    @Override
    public DavidAndCarolMarcusCreators copy() {
        return new DavidAndCarolMarcusCreators(this);
    }
}
