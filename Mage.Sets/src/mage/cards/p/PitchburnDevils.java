
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author North
 */
public final class PitchburnDevils extends CardImpl {

    public PitchburnDevils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.DEVIL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Pitchburn Devils dies, it deals 3 damage to any target.
        DiesSourceTriggeredAbility ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(3, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private PitchburnDevils(final PitchburnDevils card) {
        super(card);
    }

    @Override
    public PitchburnDevils copy() {
        return new PitchburnDevils(this);
    }
}
