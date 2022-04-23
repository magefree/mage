package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class RavenFamiliar extends CardImpl {

    public RavenFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Echo {2}{U}
        this.addAbility(new EchoAbility("{2}{U}"));
        // When Raven Familiar enters the battlefield, look at the top three cards of your library.
        // Put one of them into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new LookLibraryAndPickControllerEffect(3, 1, PutCards.HAND, PutCards.BOTTOM_ANY)));
    }

    private RavenFamiliar(final RavenFamiliar card) {
        super(card);
    }

    @Override
    public RavenFamiliar copy() {
        return new RavenFamiliar(this);
    }
}
