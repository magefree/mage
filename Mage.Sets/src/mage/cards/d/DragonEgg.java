package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.DragonEggDragonToken;

/**
 *
 * @author jeffwadsworth
 */
public final class DragonEgg extends CardImpl {

    public DragonEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.DRAGON, SubType.EGG);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When Dragon Egg dies, create a 2/2 red Dragon creature token with flying. It has "{R}: This creature gets +1/+0 until end of turn".
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new DragonEggDragonToken()), false));

    }

    private DragonEgg(final DragonEgg card) {
        super(card);
    }

    @Override
    public DragonEgg copy() {
        return new DragonEgg(this);
    }

}
