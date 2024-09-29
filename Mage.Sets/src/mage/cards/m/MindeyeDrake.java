
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class MindeyeDrake extends CardImpl {

    public MindeyeDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Mindeye Drake dies, target player puts the top five cards of their library into their graveyard.
        Ability ability = new DiesSourceTriggeredAbility(new MillCardsTargetEffect(5));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private MindeyeDrake(final MindeyeDrake card) {
        super(card);
    }

    @Override
    public MindeyeDrake copy() {
        return new MindeyeDrake(this);
    }
}
