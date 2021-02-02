
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
public final class RishadanCutpurse extends CardImpl {

    public RishadanCutpurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Rishadan Cutpurse enters the battlefield, each opponent sacrifices a permanent unless they pay {1}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeOpponentsUnlessPayEffect(1)));
    }

    private RishadanCutpurse(final RishadanCutpurse card) {
        super(card);
    }

    @Override
    public RishadanCutpurse copy() {
        return new RishadanCutpurse(this);
    }
}
