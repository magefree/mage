
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox
 */
public final class CombatMedic extends CardImpl {

    public CombatMedic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {1}{W}: Prevent the next 1 damage that would be dealt to any target this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, 1), new ManaCostsImpl<>("{1}{W}"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private CombatMedic(final CombatMedic card) {
        super(card);
    }

    @Override
    public CombatMedic copy() {
        return new CombatMedic(this);
    }
}
