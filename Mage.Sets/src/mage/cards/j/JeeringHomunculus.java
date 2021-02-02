
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class JeeringHomunculus extends CardImpl {

    public JeeringHomunculus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // When Jeering Homunculus enters the battlefield, you may goad target creature.
        // (Until your next turn, that creature attacks each combat if able and attacks a player other than you if able.)
        Ability ability = new EntersBattlefieldTriggeredAbility(new GoadTargetEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private JeeringHomunculus(final JeeringHomunculus card) {
        super(card);
    }

    @Override
    public JeeringHomunculus copy() {
        return new JeeringHomunculus(this);
    }
}
