
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class DwarvenVigilantes extends CardImpl {

    public DwarvenVigilantes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.DWARF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Dwarven Vigilantes attacks and isn't blocked, you may have it deal damage equal to its power to target creature. If you do, Dwarven Vigilantes assigns no combat damage this turn.
        Effect effect = new DamageTargetEffect(new SourcePermanentPowerCount());
        effect.setText("it deal damage equal to its power to target creature");
        Ability ability = new AttacksAndIsNotBlockedTriggeredAbility(effect, true);
        ability.addEffect(new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn, true));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DwarvenVigilantes(final DwarvenVigilantes card) {
        super(card);
    }

    @Override
    public DwarvenVigilantes copy() {
        return new DwarvenVigilantes(this);
    }
}
