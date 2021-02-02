
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.RekindlingPhoenixToken;

/**
 *
 * @author LevelX2
 */
public final class RekindlingPhoenix extends CardImpl {

    public RekindlingPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Rekindling Phoenix dies, create a 0/1 red Elemental creature token with "At the beginning of your upkeep, sacrifice this creature and return target card named Rekindling Phoenix from your graveyard to the battlefield. It gains haste until end of turn."
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new RekindlingPhoenixToken()), false));
    }

    private RekindlingPhoenix(final RekindlingPhoenix card) {
        super(card);
    }

    @Override
    public RekindlingPhoenix copy() {
        return new RekindlingPhoenix(this);
    }
}
