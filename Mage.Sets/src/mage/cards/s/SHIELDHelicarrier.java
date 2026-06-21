package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.game.permanent.token.SoldierToken;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SHIELDHelicarrier extends CardImpl {

    public SHIELDHelicarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this Vehicle enters, create two 1/1 white Soldier creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SoldierToken(), 2)));

        // Crew 6
        this.addAbility(new CrewAbility(6));
    }

    private SHIELDHelicarrier(final SHIELDHelicarrier card) {
        super(card);
    }

    @Override
    public SHIELDHelicarrier copy() {
        return new SHIELDHelicarrier(this);
    }
}
