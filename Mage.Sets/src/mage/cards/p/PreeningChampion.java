package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Elemental11BlueRedToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PreeningChampion extends CardImpl {

    public PreeningChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Preening Champion enters the battlefield, create a 1/1 blue and red Elemental creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Elemental11BlueRedToken())));
    }

    private PreeningChampion(final PreeningChampion card) {
        super(card);
    }

    @Override
    public PreeningChampion copy() {
        return new PreeningChampion(this);
    }
}
