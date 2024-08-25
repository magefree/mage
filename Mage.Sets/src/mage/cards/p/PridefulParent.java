package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.CatToken3;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PridefulParent extends CardImpl {

    public PridefulParent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When this creature enters, create a 1/1 white Cat creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new CatToken3())));
    }

    private PridefulParent(final PridefulParent card) {
        super(card);
    }

    @Override
    public PridefulParent copy() {
        return new PridefulParent(this);
    }
}
