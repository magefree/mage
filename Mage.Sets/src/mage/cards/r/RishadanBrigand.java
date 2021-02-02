
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeOpponentsUnlessPayEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Eirkei
 */
public final class RishadanBrigand extends CardImpl {

    public RishadanBrigand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Rishadan Brigand enters the battlefield, each opponent sacrifices a permanent unless they pay {3}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeOpponentsUnlessPayEffect(3)));
        
        // Rishadan Brigand can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
        
    }

    private RishadanBrigand(final RishadanBrigand card) {
        super(card);
    }

    @Override
    public RishadanBrigand copy() {
        return new RishadanBrigand(this);
    }
}
