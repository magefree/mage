package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.MapToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SpyglassSiren extends CardImpl {

    public SpyglassSiren(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");
        this.subtype.add(SubType.SIREN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Spyglass Siren enters the battlefield, create a Map Token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MapToken()), false));
    }

    private SpyglassSiren(final SpyglassSiren card) {
        super(card);
    }

    @Override
    public SpyglassSiren copy() {
        return new SpyglassSiren(this);
    }
}
