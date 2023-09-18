
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousTriggeredAbility;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class AcklayOfTheArena extends CardImpl {

    public AcklayOfTheArena(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{G}{W}");
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // {2}{R}{G}{W}: Monstrosity 1.
        this.addAbility(new MonstrosityAbility("{2}{R}{G}{W}", 1));

        // Whenever a creature you control becomes monstrous, it fights target creature an opponent controls.
        Ability ability = new BecomesMonstrousTriggeredAbility(new FightTargetsEffect().setText("it fights target creature an opponent controls"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

    }

    private AcklayOfTheArena(final AcklayOfTheArena card) {
        super(card);
    }

    @Override
    public AcklayOfTheArena copy() {
        return new AcklayOfTheArena(this);
    }
}
