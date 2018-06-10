
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.ManaSpentToCastWatcher;

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

        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(),false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new ConditionalTriggeredAbility(ability, new ManaWasSpentCondition(ColoredManaSymbol.U),
                "if {U} was spent to cast {this}, return target creature to its owner's hand."),
                new ManaSpentToCastWatcher());
    }

    public OgreSavant(final OgreSavant card) {
        super(card);
    }

    @Override
    public OgreSavant copy() {
        return new OgreSavant(this);
    }
}
