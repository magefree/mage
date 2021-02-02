
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author noxx

 */
public final class UndeadExecutioner extends CardImpl {

    public UndeadExecutioner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Undead Executioner dies, you may have target creature get -2/-2 until end of turn.
        Ability ability = new DiesSourceTriggeredAbility(new BoostTargetEffect(-2, -2, Duration.EndOfTurn), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private UndeadExecutioner(final UndeadExecutioner card) {
        super(card);
    }

    @Override
    public UndeadExecutioner copy() {
        return new UndeadExecutioner(this);
    }
}
