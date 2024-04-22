
package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class JeskaiBarricade extends CardImpl {

    public JeskaiBarricade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // When Jeskai Barricade enters the battlefield, you may return another target creature you control to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), true);
        Target target = new TargetControlledCreaturePermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private JeskaiBarricade(final JeskaiBarricade card) {
        super(card);
    }

    @Override
    public JeskaiBarricade copy() {
        return new JeskaiBarricade(this);
    }
}
