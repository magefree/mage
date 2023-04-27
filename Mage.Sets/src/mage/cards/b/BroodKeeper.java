
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AuraAttachedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.DragonEggDragonToken;

/**
 *
 * @author Quercitron
 */
public final class BroodKeeper extends CardImpl {

    public BroodKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.HUMAN, SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever an Aura becomes attached to Brood Keeper, create a 2/2 red Dragon creature token with flying.
        // It has "{R}: This creature gets +1/+0 until end of turn."
        Effect effect = new CreateTokenEffect(new DragonEggDragonToken());
        effect.setText("create a 2/2 red Dragon creature token with flying. It has \"{R}: This creature gets +1/+0 until end of turn.\"");
        this.addAbility(new AuraAttachedTriggeredAbility(effect, false));
    }

    private BroodKeeper(final BroodKeeper card) {
        super(card);
    }

    @Override
    public BroodKeeper copy() {
        return new BroodKeeper(this);
    }
}
