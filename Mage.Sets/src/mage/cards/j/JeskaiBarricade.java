
package mage.cards.j;

import java.util.UUID;
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
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class JeskaiBarricade extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature you control");
    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

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
        Target target = new TargetCreaturePermanent(filter);
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
