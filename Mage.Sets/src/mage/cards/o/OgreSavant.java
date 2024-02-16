
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author FenrisulfrX
 */
public final class OgreSavant extends CardImpl {

    public OgreSavant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        //When Ogre Savant enters the battlefield, if {U} was spent to cast Ogre Savant, return target creature to its owner’s hand.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(),false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, ManaWasSpentCondition.BLUE,
                "When {this} enters the battlefield, if {U} was spent to cast it, return target creature to its owner's hand."));
    }

    private OgreSavant(final OgreSavant card) {
        super(card);
    }

    @Override
    public OgreSavant copy() {
        return new OgreSavant(this);
    }
}
