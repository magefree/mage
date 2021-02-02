
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.OnOpponentsTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Markedagain
 */
public final class GlenElendraPranksters extends CardImpl {

    public GlenElendraPranksters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast a spell during an opponent's turn, you may return target creature you control to its owner's hand.
        Ability ability = new ConditionalTriggeredAbility(
                new SpellCastControllerTriggeredAbility(new ReturnToHandTargetEffect(), true), OnOpponentsTurnCondition.instance,
                "Whenever you cast a spell during an opponent's turn, you may return target creature you control to its owner's hand."
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private GlenElendraPranksters(final GlenElendraPranksters card) {
        super(card);
    }

    @Override
    public GlenElendraPranksters copy() {
        return new GlenElendraPranksters(this);
    }
}
