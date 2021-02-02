
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeOpponentsUnlessPayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Eirkei
 */
public final class RishadanFootpad extends CardImpl {

    public RishadanFootpad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Rishadan Footpad enters the battlefield, each opponent sacrifices a permanent unless they pay {2}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeOpponentsUnlessPayEffect(2)));
    }

    private RishadanFootpad(final RishadanFootpad card) {
        super(card);
    }

    @Override
    public RishadanFootpad copy() {
        return new RishadanFootpad(this);
    }
}
