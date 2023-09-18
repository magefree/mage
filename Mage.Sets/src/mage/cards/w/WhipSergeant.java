
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class WhipSergeant extends CardImpl {

    public WhipSergeant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {R}: Target creature gains haste until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
            new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{R}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private WhipSergeant(final WhipSergeant card) {
        super(card);
    }

    @Override
    public WhipSergeant copy() {
        return new WhipSergeant(this);
    }
}
