
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class GrotagThrasher extends CardImpl {

    public GrotagThrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.LIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Grotag Thrasher attacks, target creature can't block this turn.
        AttacksTriggeredAbility ability = new AttacksTriggeredAbility(new CantBlockTargetEffect(Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GrotagThrasher(final GrotagThrasher card) {
        super(card);
    }

    @Override
    public GrotagThrasher copy() {
        return new GrotagThrasher(this);
    }
}
