
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public final class CrimsonAcolyte extends CardImpl {

    public CrimsonAcolyte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
        
        // {W}: Target creature gains protection from red until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new GainAbilityTargetEffect(ProtectionAbility.from(ObjectColor.RED), Duration.EndOfTurn),
                new ManaCostsImpl<>("{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CrimsonAcolyte(final CrimsonAcolyte card) {
        super(card);
    }

    @Override
    public CrimsonAcolyte copy() {
        return new CrimsonAcolyte(this);
    }
}
