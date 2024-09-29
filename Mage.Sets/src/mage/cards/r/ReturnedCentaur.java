
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class ReturnedCentaur extends CardImpl {

    public ReturnedCentaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CENTAUR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Returned Centaur enters the battlefield, target player puts the top four cards of their library into their graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsTargetEffect(4));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private ReturnedCentaur(final ReturnedCentaur card) {
        super(card);
    }

    @Override
    public ReturnedCentaur copy() {
        return new ReturnedCentaur(this);
    }
}
