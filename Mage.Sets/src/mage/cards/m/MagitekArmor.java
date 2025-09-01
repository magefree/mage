package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.HeroToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagitekArmor extends CardImpl {

    public MagitekArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When this Vehicle enters, create a 1/1 colorless Hero creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HeroToken())));

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private MagitekArmor(final MagitekArmor card) {
        super(card);
    }

    @Override
    public MagitekArmor copy() {
        return new MagitekArmor(this);
    }
}
